package com.company.projectmanagement.view.task;

import com.company.projectmanagement.entity.Task;

import com.company.projectmanagement.view.main.MainView;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.view.*;

@Route(value = "tasks/:id", layout = MainView.class)
@ViewController("Task_.detail")
@ViewDescriptor("task-detail-view.xml")
@EditedEntityContainer("taskDc")
public class TaskDetailView extends StandardDetailView<Task> {
    @Subscribe("descriptionField")
    public void onDescriptionFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixTextArea, ?> event) {
        Integer length = event.getValue().toString().length();
        if (length >= 500) {
            event.getSource().setValue(event.getSource().getValue().substring(0,500));
            length = 500;
        }
        event.getSource().setHelperText(length+"/500");

    }
}