package edu.wm.cs301.wordle.model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import edu.wm.cs.cs301.wordle.model.WordleModel;

@Suite
@SelectClasses({WordleModelTest.class, WordleResponseTest.class})
public class AllTests {

}
