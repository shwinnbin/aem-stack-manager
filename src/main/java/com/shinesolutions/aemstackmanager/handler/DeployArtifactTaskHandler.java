package com.shinesolutions.aemstackmanager.handler;

import com.shinesolutions.aemstackmanager.model.TaskMessage;
import com.shinesolutions.aemstackmanager.service.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DeployArtifactTaskHandler implements TaskHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${command.deployArtifact}")
    private String command;

    @Resource
    private CommandExecutor commandExecutor;

    public boolean handleTask(TaskMessage message) {

        logger.debug("Handling Task: " + message);

        String builtCommand = command.replaceAll("\\{stack_prefix}", message.getStackPrefix());
        builtCommand = builtCommand.replaceAll("\\{component}", message.getDetails().getComponent());
        builtCommand = builtCommand.replaceAll("\\{artifact}", message.getDetails().getArtifact());

        return commandExecutor.executeReturnBoolean(builtCommand);
    }

}
