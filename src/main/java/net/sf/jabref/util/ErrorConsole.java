begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Handler
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|LogRecord
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|SimpleFormatter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_comment
comment|/**  * This class redirects the System.err stream so it goes both the way it normally  * goes, and into a ByteArrayOutputStream. We can use this stream to display any  * error messages and stack traces to the user. Such an error console can be  * useful in getting complete bug reports, especially from Windows users,  * without asking users to run JabRef in a command window to catch the error info.  *  * It also offers a separate tab for the log output.  *  * User: alver  * Date: Mar 1, 2006  * Time: 11:13:03 PM  */
end_comment

begin_class
DECL|class|ErrorConsole
specifier|public
class|class
name|ErrorConsole
extends|extends
name|Handler
block|{
DECL|field|errByteStream
specifier|private
specifier|final
name|ByteArrayOutputStream
name|errByteStream
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
DECL|field|outByteStream
specifier|private
specifier|final
name|ByteArrayOutputStream
name|outByteStream
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
DECL|field|logOutput
specifier|private
specifier|final
name|ArrayList
argument_list|<
name|String
argument_list|>
name|logOutput
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|logOutputCache
specifier|private
name|String
name|logOutputCache
init|=
literal|""
decl_stmt|;
DECL|field|logOutputCacheRefreshNeeded
specifier|private
name|boolean
name|logOutputCacheRefreshNeeded
init|=
literal|true
decl_stmt|;
DECL|field|fmt
specifier|private
specifier|final
name|SimpleFormatter
name|fmt
init|=
operator|new
name|SimpleFormatter
argument_list|()
decl_stmt|;
DECL|field|MAXLOGLINES
specifier|private
specifier|static
specifier|final
name|int
name|MAXLOGLINES
init|=
literal|500
decl_stmt|;
DECL|field|instance
specifier|private
specifier|static
name|ErrorConsole
name|instance
init|=
literal|null
decl_stmt|;
DECL|method|getInstance ()
specifier|public
specifier|static
name|ErrorConsole
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|ErrorConsole
operator|.
name|instance
operator|==
literal|null
condition|)
block|{
name|ErrorConsole
operator|.
name|instance
operator|=
operator|new
name|ErrorConsole
argument_list|()
expr_stmt|;
block|}
return|return
name|ErrorConsole
operator|.
name|instance
return|;
block|}
DECL|method|ErrorConsole ()
specifier|private
name|ErrorConsole
parameter_list|()
block|{
name|PrintStream
name|myErr
init|=
operator|new
name|PrintStream
argument_list|(
name|errByteStream
argument_list|)
decl_stmt|;
name|PrintStream
name|tee
init|=
operator|new
name|TeeStream
argument_list|(
name|System
operator|.
name|err
argument_list|,
name|myErr
argument_list|)
decl_stmt|;
name|System
operator|.
name|setErr
argument_list|(
name|tee
argument_list|)
expr_stmt|;
name|myErr
operator|=
operator|new
name|PrintStream
argument_list|(
name|outByteStream
argument_list|)
expr_stmt|;
name|tee
operator|=
operator|new
name|TeeStream
argument_list|(
name|System
operator|.
name|out
argument_list|,
name|myErr
argument_list|)
expr_stmt|;
name|System
operator|.
name|setOut
argument_list|(
name|tee
argument_list|)
expr_stmt|;
block|}
DECL|method|getErrorMessages ()
specifier|private
name|String
name|getErrorMessages
parameter_list|()
block|{
return|return
name|errByteStream
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getOutput ()
specifier|private
name|String
name|getOutput
parameter_list|()
block|{
return|return
name|outByteStream
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getLog ()
specifier|private
name|String
name|getLog
parameter_list|()
block|{
if|if
condition|(
name|logOutputCacheRefreshNeeded
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|line
range|:
name|logOutput
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
name|logOutputCache
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
return|return
name|logOutputCache
return|;
block|}
comment|/**      *       * @param tabbed the tabbed pane to add the tab to      * @param output the text to display in the tab      * @param ifEmpty Text to output if textbox is emtpy. may be null      */
DECL|method|addTextArea (JTabbedPane tabbed, String title, String output, String ifEmpty)
specifier|private
name|void
name|addTextArea
parameter_list|(
name|JTabbedPane
name|tabbed
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|output
parameter_list|,
name|String
name|ifEmpty
parameter_list|)
block|{
name|JTextArea
name|ta
init|=
operator|new
name|JTextArea
argument_list|(
name|output
argument_list|)
decl_stmt|;
name|ta
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|ifEmpty
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|ta
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|ta
operator|.
name|setText
argument_list|(
name|ifEmpty
argument_list|)
expr_stmt|;
block|}
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|ta
argument_list|)
decl_stmt|;
name|tabbed
operator|.
name|addTab
argument_list|(
name|title
argument_list|,
name|sp
argument_list|)
expr_stmt|;
block|}
DECL|method|displayErrorConsole (JFrame parent)
specifier|private
name|void
name|displayErrorConsole
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|JTabbedPane
name|tabbed
init|=
operator|new
name|JTabbedPane
argument_list|()
decl_stmt|;
name|addTextArea
argument_list|(
name|tabbed
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Output"
argument_list|)
argument_list|,
name|getOutput
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|addTextArea
argument_list|(
name|tabbed
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Exceptions"
argument_list|)
argument_list|,
name|getErrorMessages
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"No exceptions have ocurred."
argument_list|)
argument_list|)
expr_stmt|;
name|addTextArea
argument_list|(
name|tabbed
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Log"
argument_list|)
argument_list|,
name|getLog
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|tabbed
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|500
argument_list|,
literal|500
argument_list|)
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
name|tabbed
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Program output"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
DECL|class|ErrorConsoleAction
class|class
name|ErrorConsoleAction
extends|extends
name|AbstractAction
block|{
DECL|field|frame
specifier|final
name|JFrame
name|frame
decl_stmt|;
DECL|method|ErrorConsoleAction (JFrame frame)
specifier|public
name|ErrorConsoleAction
parameter_list|(
name|JFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|menuTitle
argument_list|(
literal|"Show error console"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Display all error messages"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|displayErrorConsole
argument_list|(
name|frame
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getAction (JFrame parent)
specifier|public
name|AbstractAction
name|getAction
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
return|return
operator|new
name|ErrorConsoleAction
argument_list|(
name|parent
argument_list|)
return|;
block|}
comment|// All writes to this print stream are copied to two print streams
DECL|class|TeeStream
specifier|public
specifier|static
class|class
name|TeeStream
extends|extends
name|PrintStream
block|{
DECL|field|out
specifier|final
name|PrintStream
name|out
decl_stmt|;
DECL|method|TeeStream (PrintStream out1, PrintStream out2)
specifier|public
name|TeeStream
parameter_list|(
name|PrintStream
name|out1
parameter_list|,
name|PrintStream
name|out2
parameter_list|)
block|{
name|super
argument_list|(
name|out1
argument_list|)
expr_stmt|;
name|this
operator|.
name|out
operator|=
name|out2
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|write (byte[] buf, int off, int len)
specifier|public
name|void
name|write
parameter_list|(
name|byte
index|[]
name|buf
parameter_list|,
name|int
name|off
parameter_list|,
name|int
name|len
parameter_list|)
block|{
try|try
block|{
name|super
operator|.
name|write
argument_list|(
name|buf
argument_list|,
name|off
argument_list|,
name|len
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|buf
argument_list|,
name|off
argument_list|,
name|len
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ignored
parameter_list|)
block|{             }
block|}
annotation|@
name|Override
DECL|method|flush ()
specifier|public
name|void
name|flush
parameter_list|()
block|{
name|super
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
comment|/* * * methods for Logging (required by Handler) * * */
annotation|@
name|Override
DECL|method|close ()
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|SecurityException
block|{     }
annotation|@
name|Override
DECL|method|flush ()
specifier|public
name|void
name|flush
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|publish (LogRecord record)
specifier|public
name|void
name|publish
parameter_list|(
name|LogRecord
name|record
parameter_list|)
block|{
name|String
name|msg
init|=
name|fmt
operator|.
name|format
argument_list|(
name|record
argument_list|)
decl_stmt|;
name|logOutput
operator|.
name|add
argument_list|(
name|msg
argument_list|)
expr_stmt|;
if|if
condition|(
name|logOutput
operator|.
name|size
argument_list|()
operator|<
name|ErrorConsole
operator|.
name|MAXLOGLINES
condition|)
block|{
comment|// if we did not yet reach MAXLOGLINES, we just append the string to the cache
name|logOutputCache
operator|=
name|logOutputCache
operator|+
name|msg
expr_stmt|;
block|}
else|else
block|{
comment|// if we reached MAXLOGLINES, we switch to the "real" caching method and remove old lines
name|logOutputCacheRefreshNeeded
operator|=
literal|true
expr_stmt|;
while|while
condition|(
name|logOutput
operator|.
name|size
argument_list|()
operator|>
name|ErrorConsole
operator|.
name|MAXLOGLINES
condition|)
block|{
comment|// if log is too large, remove first line
comment|// we need a while loop as the formatter may output more than one line
name|logOutput
operator|.
name|remove
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
name|logOutputCacheRefreshNeeded
operator|=
literal|true
expr_stmt|;
block|}
block|}
end_class

end_unit

