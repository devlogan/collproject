package com.collbackend.daos;

import java.util.List;

import com.collbackend.models.Job;

public interface JobDao {

	void addJob(Job job);


	List<Job> getAllJobs();

}
