package com.rsm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rsm.models.Resource;
import com.rsm.models.Task;

import com.rsm.dao.interfaces.ResourceMatchingDao;
import com.rsm.dto.TaskResourcesDto;

/**
 * @author Rathish
 */
public class ResourceMatchingDaoImpl implements ResourceMatchingDao{
	
	public List<Task> getAllTasksForProject(String projectId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		session.getTransaction().begin();
		SQLQuery query=session.createSQLQuery("select t.task_id, t.name FROM tasks t "
				+ "where t.project_id = :projectId");
		query.setParameter("projectId",projectId);
		List<Object[]> rows = query.list();
		List<Task> tasks = new ArrayList<Task>();
		for(Object[] row : rows){
			Task task = new Task();
			task.setTaskId(row[0].toString());
			task.setTaskName(row[1].toString());
			tasks.add(task);
		}
		System.out.println("sss" + tasks.toString());
		session.flush();
	    session.getTransaction().commit();
	    return tasks;
	}	
	
	public String getSkillForTask(String taskId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		SQLQuery innerQuery = session.createSQLQuery("select skill_id from tasks where task_id = :taskId");
		innerQuery.setParameter("taskId",taskId);
		String skillId = (String)innerQuery.uniqueResult();
		session.flush();
	    session.getTransaction().commit();
		return skillId;
	}
	
	public List<TaskResourcesDto> getListOfResources(String projectId){
		System.out.println("Method 2");
		List<Task> tasks = getAllTasksForProject(projectId);
		
		List<TaskResourcesDto> tasksResourcesList = new ArrayList<TaskResourcesDto>();
		for(Task task: tasks) {
			
			String skillId = getSkillForTask(task.getTaskId());
			System.out.println("Task iteration: " + task.getTaskId());
			System.out.println("name: " + task.getTaskName());
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			SQLQuery query = session.createSQLQuery("select r.resource_id, r.name from "
					+ "resources r join resources_skills rs "
					+ "on r.resource_id = rs.resource_id where rs.skill_id"
					+ " = :skillId");           
			query.setParameter("skillId",skillId);
			
			List<Object[]> rows = query.list();
			session.flush();
		    session.getTransaction().commit();
			List<Resource> resources = new ArrayList<Resource>();
			for(Object[] row : rows){
				Resource resource = new Resource();
				resource.setResourceId(row[0].toString());
				resource.setResourceName(row[1].toString());
				System.out.println(resource.getResourceId() + resource.getResourceName());
				resources.add(resource);
			}
			TaskResourcesDto taskResource = new TaskResourcesDto(resources, 
					task.getTaskId(), task.getTaskName());
			tasksResourcesList.add(taskResource);
		}
		return tasksResourcesList;
	}

}
