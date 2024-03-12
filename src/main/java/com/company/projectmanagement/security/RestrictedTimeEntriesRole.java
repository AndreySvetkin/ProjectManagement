package com.company.projectmanagement.security;

import com.company.projectmanagement.entity.TimeEntry;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "RestrictedTimeEntriesRole", code = RestrictedTimeEntriesRole.CODE)
public interface RestrictedTimeEntriesRole {
    String CODE = "restricted-time-entries";

    @JpqlRowLevelPolicy(entityClass = TimeEntry.class,
            where = "{E}.user.id = :current_user_id or {E}.task.project.manager.id = :current_user_id")
    void timeEntry();
}