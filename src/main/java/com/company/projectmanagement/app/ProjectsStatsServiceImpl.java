package com.company.projectmanagement.app;

import com.company.projectmanagement.entity.Project;
import com.company.projectmanagement.entity.ProjectStats;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectsStatsServiceImpl implements ProjectsStatsService {

    private final DataManager dataManager;

    public ProjectsStatsServiceImpl(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<ProjectStats> fetchProjectsStatistics(){
        List<Project> projects = dataManager.load(Project.class).all().list();

        List<ProjectStats> projectStats = projects.stream().map(project -> {
            ProjectStats stat = dataManager.create(ProjectStats.class);

            stat.setId(project.getId());
            stat.setProjectName(project.getName());
            stat.setTasksCount(project.getTasks().size());
            Integer plannedEfforts = calcPlannedEfforts(project);
            stat.setPlannedEfforts(plannedEfforts);
            Integer actualEfforts = calcActualEfforts(project);
            stat.setActualEfforts(actualEfforts);

            return stat;
        }).collect(Collectors.toList());

        return projectStats;
    }

    private Integer calcPlannedEfforts(Project project){
        return dataManager.loadValue("select sum(t.estimation) from Task_ t where t.project = :project",Integer.class)
                .parameter("project",project)
                .one();
    }
    private Integer calcActualEfforts(Project project){
        return dataManager.loadValue("select sum(te.timeSpent) from TimeEntry te where te.task.project = :project",Integer.class)
                .parameter("project",project)
                .one();
    }
}