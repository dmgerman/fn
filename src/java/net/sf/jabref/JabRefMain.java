begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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

begin_comment
comment|/**  * This is a class compiled under Java 1.4.2 that will start the real JabRef and  * print some warnings if no Java 1.5 and higher and no JRE from Sun  * Microsystems is found.  *   * Caution: We cannot use any other class from JabRef here (for instance no  * calls to Globals.lang() are possible), since then it could not be run using  * Java 1.4.  *   * @author oezbek  *   */
end_comment

begin_class
DECL|class|JabRefMain
specifier|public
class|class
name|JabRefMain
block|{
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
literal|"1.5"
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
literal|"WARNING: You are running Java version 1.4 or lower ("
operator|+
name|javaVersion
operator|+
literal|" to be exact).\n"
operator|+
literal|"         JabRef needs at least a Java Runtime Environment 1.5 or higher.\n"
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
name|javaVendor
operator|.
name|indexOf
argument_list|(
literal|"Sun Microsystems"
argument_list|)
operator|==
operator|-
literal|1
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
literal|"WARNING: You are not running a Java version from Sun Microsystems.\n"
operator|+
literal|"         Your java vendor is: "
operator|+
name|javaVendor
operator|+
literal|"\n"
operator|+
literal|"         If JabRef crashes please consider switching to a Sun Java Runtime.\n"
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
operator|new
name|Class
index|[]
block|{
name|args
operator|.
name|getClass
argument_list|()
block|}
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"ERROR while starting or running JabRef:\n"
argument_list|)
expr_stmt|;
name|e
operator|.
name|getCause
argument_list|()
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Please tell the JabRef developers about this by writing a bug report.\n"
operator|+
literal|"You can find our bug tracker at http://sourceforge.net/tracker/?atid=600306&group_id=92314\n"
operator|+
literal|"If the bug has already been reported there, please add your comments to the existing bug.\n"
operator|+
literal|"If the bug has not been reported yet, then we need the complete error message given above\n"
operator|+
literal|"and a description of what you did before the error occured.\n"
operator|+
literal|"In most cases we also need your JabRef version, the java version\n"
operator|+
literal|"(use 'java -version' for this) and the operating system you are using.\n"
operator|+
literal|"\n"
operator|+
literal|"We are sorry for the trouble and thanks for reporting problems with JabRef!\n"
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
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\nThis means that your Java version ("
operator|+
name|javaVersion
operator|+
literal|") is not high enough to run JabRef.\nPlease update your Java Runtime Environment to a version 1.5 or higher.\n"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

