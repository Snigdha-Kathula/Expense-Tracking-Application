package com.example.splitwise1;

import com.example.splitwise1.commands.*;
import com.example.splitwise1.controllers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@SpringBootApplication
@EnableJpaAuditing
public class SplitWise1Application implements CommandLineRunner {
    @Autowired
    CommandExecutor commandExecutor;
    @Autowired
    UserController userController;
    @Autowired
    ProfileController profileController;
    @Autowired
    GroupController addGroupController;
    @Autowired
    ExpenseController expenseController;
    @Autowired
    SettleController settleController;

    public static void main(String[] args) {
        SpringApplication.run(SplitWise1Application.class, args);
    }

    @Override
    public void run(String... args){
        commandExecutor.addCommand(new RegisterCommand(userController));
        commandExecutor.addCommand(new UpdateProfileCommand(profileController));
        commandExecutor.addCommand(new AddGroupCommand(addGroupController));
        commandExecutor.addCommand(new AddMemberCommand(addGroupController));
        commandExecutor.addCommand(new GroupsCommand(addGroupController));
        commandExecutor.addCommand(new ExpenseCommand(expenseController));
        commandExecutor.addCommand(new MyTotalCommand(settleController));
        commandExecutor.addCommand(new SettleUpUserCommand(settleController));
        commandExecutor.addCommand(new SettleUpGroupCommand(settleController));
        while(true){
            System.out.println("Enter the command : ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("exit")){
                return;
            }
            commandExecutor.execute(input);
        }
    }
}
