package org.example.CommandBuilders;

import org.example.Exceptions.BadFormatCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupCommandBuilderTest
{
        @Test
        public void appendLine_inputCorrectLine_shouldNotThrowException()
        {
            GroupCommandBuilder builder = new GroupCommandBuilder();

            Assertions.assertDoesNotThrow(() -> builder.appendLine("group A, A1, A2 as AAA"));
            Assertions.assertDoesNotThrow(() -> builder.appendLine("   group   A  , A1 ,  A2   as    AAA"));
        }

        @Test
        public void appendLine_inputInCorrectLineWithoutFiguresName_shouldThrowException()
        {
            GroupCommandBuilder builder = new GroupCommandBuilder();
            String commandLine = "group as AAA";

            BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                    ()-> builder.appendLine(commandLine));

            Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + GroupCommandBuilder.class.getName() +
                    " { Problem in: figures count should be more 1 }",
                    exception.getMessage());
        }

        @Test
        public void appendLine_inputInCorrectLineWithoutGroupName_shouldThrowException()
        {
            GroupCommandBuilder builder = new GroupCommandBuilder();
            String commandLine = "group A, A1, A2 as";

            BadFormatCommandException exception = Assertions.assertThrows(BadFormatCommandException.class,
                    ()-> builder.appendLine(commandLine));

            Assertions.assertEquals("Command: { " + commandLine + " } has the wrong format in the " + GroupCommandBuilder.class.getName() +
                    " { Problem in: group name not be found }",
                    exception.getMessage());
        }


        @Test
        public void isCommandReady_calledBeforeAppendLine_shouldReturnFalse()
        {
            GroupCommandBuilder builder = new  GroupCommandBuilder();
            Assertions.assertFalse(builder.isCommandReady());
        }

        @Test
        public void isCommandReady_calledAfterAnyThrowInClass_shouldReturnFalse()
        {
            GroupCommandBuilder builder = new  GroupCommandBuilder();

            try
            {
                builder.appendLine("Bad line");
            }
            catch(BadFormatCommandException _)
            {}

            Assertions.assertFalse(builder.isCommandReady());
        }

        @Test
        public void isCommandReady_calledAfterCorrectAppendLine_shouldReturnTrue()
        {
            GroupCommandBuilder builder = new  GroupCommandBuilder();
            builder.appendLine("group A, A1, A2 as AAA");
            Assertions.assertTrue(builder.isCommandReady());
        }
}
