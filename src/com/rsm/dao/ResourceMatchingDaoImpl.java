package com.rsm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rsm.models.Resource;
import com.rsm.models.Task;
import com.rsm.api.exceptions.DBException;
import com.rsm.api.exceptions.InvalidProjectIdException;
import com.rsm.dao.interfaces.ResourceMatchingDao;
import com.rsm.dto.TaskResourcesDto;

/**
 * @author Rathish
 */
public class ResourceMatchingDaoImpl implements ResourceMatchingDao{
	private final static Logger LOGGER = 
            Logger.getLogger(ResourceMatchingDaoImpl.class.getName());
	
	public List<Task> getAllTasksForProject(String projectId) throws DBException{
		List<Task> tasks = null;
		LOGGER.log(Level.INFO, "Method call - getAllTasksForProject");
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			SQLQuery query=session.createSQLQuery("select t.task_id, t.name, t.start_date, "
					+ "t.end_date FROM tasks t "
					+ "where t.project_id = :projectId");
			query.setParameter("projectId",projectId);
			List<Object[]> rows = query.list();
			tasks = new ArrayList<Task>();
			if(rows.isEmpty()) {
				LOGGER.log(Level.SEVERE, "No tasks found for the given project.");
				throw new InvalidProjectIdException("invalid");
			}
			for(Object[] row : rows){
				Task task = new Task();
				task.setTaskId(row[0].toString());
				task.setTaskName(row[1].toString());
				task.setStartDate(new Date(Long.parseLong(row[2].toString())));
				task.setEndDate(new Date(Long.parseLong(row[3].toString())));
				tasks.add(task);
			}
			session.flush();
		    session.getTransaction().commit();
		} catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "DB exception occurred");
			throw new DBException("DB");
		}
		return tasks;
	}	
	
	public String getSkillForTask(String taskId) throws DBException{
		String skillId = null;
		LOGGER.log(Level.INFO, "Method call - getSkillForTask");
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			SQLQuery innerQuery = session.createSQLQuery("select skill_id from tasks where task_id = :taskId");
			innerQuery.setParameter("taskId",taskId);
			skillId = (String)innerQuery.uniqueResult();
			session.flush();
		    session.getTransaction().commit();
		} catch(Exception ex) {
			throw new DBException("DB");
		}
		return skillId;
	}
	
	public List<TaskResourcesDto> getListOfResources(String projectId) throws DBException{
		LOGGER.log(Level.INFO, "Method call - getListOfResources");
		if(projectId.isEmpty()) {
			throw new InvalidProjectIdException("invalid");
		}
		List<TaskResourcesDto> tasksResourcesList = null;
		try {
			List<Task> tasks = getAllTasksForProject(projectId);
			
			tasksResourcesList = new ArrayList<TaskResourcesDto>();
			for(Task task: tasks) {
				
				String skillId = getSkillForTask(task.getTaskId());
				Session session = HibernateUtil.getSessionFactory().openSession();
				session.getTransaction().begin();
				SQLQuery query = session.createSQLQuery("select r.resource_id, r.name from "
						+ "resources r join resources_skills rs on r.resource_id = rs.resource_id"
						+ " where rs.skill_id = :skillId");           
				query.setParameter("skillId",skillId);
				
				List<Object[]> rows = query.list();
				session.flush();
			    session.getTransaction().commit();
				List<Resource> resources = new ArrayList<Resource>();
				for(Object[] row : rows){
					Resource resource = new Resource();
					resource.setResourceId(row[0].toString());
					resource.setResourceName(row[1].toString());
					
					Session sess = HibernateUtil.getSessionFactory().openSession();
					Transaction tx = sess.getTransaction();
					tx.begin();
					SQLQuery q = session.createSQLQuery("select r.start_date, r.end_date from "
							+ "resource_availability r where r.resource_id = :resourceId");           
					q.setParameter("resourceId",resource.getResourceId());
					List<Object[]> dates = q.list();
					session.flush();
					if (!tx.wasCommitted())
						tx.commit();
					for(Object[] date_interval : dates){
						Date resource_start_date = new Date(Long.parseLong(date_interval[0].toString()));
						Date resource_end_date = new Date(Long.parseLong(date_interval[1].toString()));
						if(task.getStartDate().after(resource_start_date) 
								&& task.getEndDate().before(resource_end_date)) {
							resources.add(resource);
							break;
						}
					}
				}
				TaskResourcesDto taskResource = new TaskResourcesDto(resources, 
						task.getTaskId(), task.getTaskName());
				tasksResourcesList.add(taskResource);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new DBException("DB");
		}
		return tasksResourcesList;
	}

}
