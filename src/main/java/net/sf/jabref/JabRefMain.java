begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

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
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JEditorPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_comment
comment|/**  * This is a class compiled under Java 1.4.2 that will start the real JabRef and  * print some warnings if no Java 1.5 and higher and no JRE from Sun  * Microsystems is found.  *   * Caution: We cannot use any other class from JabRef here (for instance no  * calls to Globals.lang() are possible), since then it could not be run using  * Java 1.4.  *   * @author oezbek  *   */
end_comment

begin_class
DECL|class|JabRefMain
specifier|public
class|class
name|JabRefMain
block|{
DECL|method|exceptionToString (Throwable t)
specifier|public
specifier|static
name|String
name|exceptionToString
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|StringWriter
name|stackTraceWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|t
operator|.
name|printStackTrace
argument_list|(
operator|new
name|PrintWriter
argument_list|(
name|stackTraceWriter
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|stackTraceWriter
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * @param args      *            We will pass these arguments to JabRef later.      */
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|String
name|javaVersion
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.version"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|javaVersion
operator|.
name|compareTo
argument_list|(
literal|"1.6"
argument_list|)
operator|<
literal|0
condition|)
block|{
name|String
name|javaVersionWarning
init|=
literal|"\n"
operator|+
literal|"WARNING: You are running Java version 1.6 or lower ("
operator|+
name|javaVersion
operator|+
literal|" to be exact).\n"
operator|+
literal|"         JabRef needs at least a Java Runtime Environment 1.6 or higher.\n"
operator|+
literal|"         JabRef should not start properly and output an error message\n"
operator|+
literal|"         (probably java.lang.UnsupportedClassVersionError ... (Unsupported major.minor version 49.0)\n"
operator|+
literal|"         See http://jabref.sf.net/faq.php for more information.\n"
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|javaVersionWarning
argument_list|)
expr_stmt|;
block|}
name|String
name|javaVendor
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.vendor"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
operator|!
name|javaVendor
operator|.
name|contains
argument_list|(
literal|"Sun Microsystems"
argument_list|)
operator|)
operator|&&
operator|(
operator|!
name|javaVendor
operator|.
name|contains
argument_list|(
literal|"Oracle"
argument_list|)
operator|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\n"
operator|+
literal|"WARNING: You are not running a Java version from Oracle (or Sun Microsystems).\n"
operator|+
literal|"         Your java vendor is: "
operator|+
name|javaVendor
operator|+
literal|"\n"
operator|+
literal|"         If JabRef crashes please consider switching to an Oracle Java Runtime.\n"
operator|+
literal|"         See http://jabref.sf.net/faq.php for more information.\n"
argument_list|)
expr_stmt|;
block|}
try|try
block|{
comment|// We need to load this class dynamically, or otherwise the Java
comment|// runtime would crash while loading JabRefMain itself.
name|Method
name|method
init|=
name|Class
operator|.
name|forName
argument_list|(
literal|"net.sf.jabref.JabRef"
argument_list|)
operator|.
name|getMethod
argument_list|(
literal|"main"
argument_list|,
name|args
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
name|method
operator|.
name|invoke
argument_list|(
literal|null
argument_list|,
operator|new
name|Object
index|[]
block|{
name|args
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
name|String
name|errorMessage
init|=
literal|"\nERROR while starting or running JabRef:\n\n"
operator|+
name|exceptionToString
argument_list|(
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
operator|+
literal|"\n"
operator|+
literal|"Please first check if this problem and a solution is already known. Find our...\n"
operator|+
literal|"  * ...FAQ at http://jabref.sf.net/faq.php and our...\n"
operator|+
literal|"  * ...user mailing-list at http://sf.net/mailarchive/forum.php?forum_name=jabref-users\n\n"
operator|+
literal|"If you do not find a solution there, please let us know about the problem by writing a bug report.\n"
operator|+
literal|"You can find our bug tracker at http://sourceforge.net/p/jabref/bugs/\n\n"
operator|+
literal|"  * If the bug has already been reported there, please add your comments to the existing bug.\n"
operator|+
literal|"  * If the bug has not been reported yet, then we need the complete error message given above\n"
operator|+
literal|"    and a description of what you did before the error occured.\n\n"
operator|+
literal|"We also need the following information (you can copy and paste all this):\n"
operator|+
literal|"  * Java Version: "
operator|+
name|javaVersion
operator|+
literal|"\n"
operator|+
literal|"  * Java Vendor: "
operator|+
name|javaVendor
operator|+
literal|"\n"
operator|+
literal|"  * Operating System: "
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.name"
argument_list|)
operator|+
literal|" ("
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.version"
argument_list|)
operator|+
literal|")\n"
operator|+
literal|"  * Hardware Architecture: "
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.arch"
argument_list|)
operator|+
literal|"\n\n"
operator|+
literal|"We are sorry for the trouble and thanks for reporting problems with JabRef!\n"
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|errorMessage
argument_list|)
expr_stmt|;
name|JEditorPane
name|pane
init|=
operator|new
name|JEditorPane
argument_list|(
literal|"text/html"
argument_list|,
literal|"<html>The following error occurred while running JabRef:<p><font color=\"red\">"
operator|+
name|exceptionToString
argument_list|(
name|e
operator|.
name|getCause
argument_list|()
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\n"
argument_list|,
literal|"<br>"
argument_list|)
operator|+
literal|"</font></p>"
operator|+
literal|"<p>Please first check if this problem and a solution is already known. Find our...</p>"
operator|+
literal|"<ul><li>...FAQ at<b>http://jabref.sf.net/faq.php</b> and our..."
operator|+
literal|"<li>...user mailing-list at<b>http://sf.net/mailarchive/forum.php?forum_name=jabref-users</b></ul>"
operator|+
literal|"If you do not find a solution there, please let us know about the problem by writing a bug report.<br>"
operator|+
literal|"You can find our bug tracker at<a href=\"http://sourceforge.net/p/jabref/bugs/\"><b>http://sourceforge.net/p/jabref/bugs/</b></a>.<br>"
operator|+
literal|"<ul><li>If the bug has already been reported there, please add your comments to the existing bug.<br>"
operator|+
literal|"<li>If the bug has not been reported yet, then we need the complete error message given above<br>"
operator|+
literal|"and a description of what you did before the error occured.</ul>"
operator|+
literal|"We also need the following information (you can copy and paste all this):</p>"
operator|+
literal|"<ul><li>Java Version: "
operator|+
name|javaVersion
operator|+
literal|"<li>Java Vendor: "
operator|+
name|javaVendor
operator|+
literal|"<li>Operating System: "
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.name"
argument_list|)
operator|+
literal|" ("
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.version"
argument_list|)
operator|+
literal|")"
operator|+
literal|"<li>Hardware Architecture: "
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.arch"
argument_list|)
operator|+
literal|"</ul>"
operator|+
literal|"We are sorry for the trouble and thanks for reporting problems with JabRef!</html>"
argument_list|)
decl_stmt|;
name|pane
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pane
operator|.
name|putClientProperty
argument_list|(
name|JEditorPane
operator|.
name|HONOR_DISPLAY_PROPERTIES
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
name|Component
name|componentToDisplay
decl_stmt|;
if|if
condition|(
name|pane
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|getHeight
argument_list|()
operator|>
literal|700
condition|)
block|{
name|JScrollPane
name|sPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|pane
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
decl_stmt|;
name|sPane
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|()
argument_list|)
expr_stmt|;
name|sPane
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
operator|(
name|int
operator|)
name|pane
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|getWidth
argument_list|()
operator|+
literal|30
argument_list|,
literal|700
argument_list|)
argument_list|)
expr_stmt|;
name|componentToDisplay
operator|=
name|sPane
expr_stmt|;
block|}
else|else
block|{
name|componentToDisplay
operator|=
name|pane
expr_stmt|;
block|}
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|componentToDisplay
argument_list|,
literal|"An error occurred while running JabRef"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"ERROR: You are running JabRef in a sandboxed"
operator|+
literal|" environment that does not allow it to be started."
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"This error should not happen."
operator|+
literal|" Write an email to the JabRef developers and tell them 'NoSuchMethodException in JabRefMain'"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"This error should not happen."
operator|+
literal|" Write an email to the JabRef developers and tell them 'ClassNotFoundException in JabRefMain'"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"This error should not happen."
operator|+
literal|" Write an email to the JabRef developers and tell them 'IllegalArgumentException in JabRefMain'"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"This error should not happen."
operator|+
literal|" Write an email to the JabRef developers and tell them 'IllegalAccessException in JabRefMain'"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedClassVersionError
name|e
parameter_list|)
block|{
name|String
name|errorMessage
init|=
name|exceptionToString
argument_list|(
name|e
argument_list|)
operator|+
literal|"\n"
operator|+
literal|"This means that your Java version ("
operator|+
name|javaVersion
operator|+
literal|") is not high enough to run JabRef.\n"
operator|+
literal|"Please update your Java Runtime Environment to a version 1.6 or higher.\n"
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|errorMessage
argument_list|)
expr_stmt|;
name|JEditorPane
name|pane
init|=
operator|new
name|JEditorPane
argument_list|(
literal|"text/html"
argument_list|,
literal|"<html>You are using Java version "
operator|+
name|javaVersion
operator|+
literal|", but JabRef needs version 1.6 or higher."
operator|+
literal|"<p>Please update your Java Runtime Environment.</p>"
operator|+
literal|"<p>For more information visit<b>http://jabref.sf.net/faq.php</b>.</p></html>"
argument_list|)
decl_stmt|;
name|pane
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pane
operator|.
name|putClientProperty
argument_list|(
name|JEditorPane
operator|.
name|HONOR_DISPLAY_PROPERTIES
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|pane
argument_list|,
literal|"Insufficient Java Version Installed"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

