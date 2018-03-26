package com.gspann.itrack.cucumber.stepdefs;

import com.gspann.itrack.ItrackApplication;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ItrackApplication.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
