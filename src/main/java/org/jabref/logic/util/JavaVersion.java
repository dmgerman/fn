begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Scanner
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_comment
comment|/**  * Provides simple checks to ensure the correct version for JabRef is available. Currently, we need to make sure that we  * have Java 1.8 but not Java 9. The functions here are not intended for direct use. Instead, they are called inside  * {@link BuildInfo}, which has the required java version string (e.g. 1.8.0_144) available through the build system.  * The version check should always happen through the<code>Globals#BUILD_INFO</code> instance which is available at a  * very early stage in the JabRef startup.  */
end_comment

begin_class
DECL|class|JavaVersion
specifier|public
class|class
name|JavaVersion
block|{
comment|// See http://openjdk.java.net/jeps/223
DECL|field|DELIMITER
specifier|private
specifier|static
specifier|final
name|Pattern
name|DELIMITER
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[._\\-+]"
argument_list|)
decl_stmt|;
DECL|field|JAVA_VERSION
specifier|private
specifier|final
name|String
name|JAVA_VERSION
decl_stmt|;
DECL|method|JavaVersion ()
specifier|public
name|JavaVersion
parameter_list|()
block|{
comment|// Be adventurous and assume that we can always access this property!
name|JAVA_VERSION
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.version"
argument_list|)
expr_stmt|;
block|}
DECL|method|JavaVersion (final String version)
specifier|public
name|JavaVersion
parameter_list|(
specifier|final
name|String
name|version
parameter_list|)
block|{
name|JAVA_VERSION
operator|=
name|version
expr_stmt|;
block|}
comment|/**      * Tries to determine if we are running on Java 9. This test should return false, when we cannot extract the correct      * Java version. Note that Java 9 has a different version scheme like "9-internal".      *      * @return true if Java 9 is used      */
DECL|method|isJava9 ()
specifier|public
name|boolean
name|isJava9
parameter_list|()
block|{
if|if
condition|(
name|JAVA_VERSION
operator|!=
literal|null
condition|)
block|{
comment|// Since isAtLeast is very optimistic, we first need to check if we have a "number" in the version string
comment|// at all. Otherwise we would get false-positives.
specifier|final
name|Scanner
name|scanner
init|=
operator|new
name|Scanner
argument_list|(
name|JAVA_VERSION
argument_list|)
decl_stmt|;
name|scanner
operator|.
name|useDelimiter
argument_list|(
name|DELIMITER
argument_list|)
expr_stmt|;
if|if
condition|(
name|scanner
operator|.
name|hasNextInt
argument_list|()
condition|)
block|{
return|return
name|isAtLeast
argument_list|(
literal|"1.9"
argument_list|)
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * A very optimistic test for ensuring we at least have a minimal required Java version. It will not fail when we      * cannot determine the result. In essence, this method splits a version string using {@link      * JavaVersion#DELIMITER} and compares two version number by number.      *      * @param version Should be in the form X.X.X_XXX where X are integers.      * @return true if the numbers in version available for comparison are all greater-equals the currently running Java      * version.      */
DECL|method|isAtLeast (final String version)
specifier|public
name|boolean
name|isAtLeast
parameter_list|(
specifier|final
name|String
name|version
parameter_list|)
block|{
if|if
condition|(
name|JAVA_VERSION
operator|==
literal|null
operator|||
name|version
operator|==
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
specifier|final
name|Scanner
name|scannerRunningVersion
init|=
operator|new
name|Scanner
argument_list|(
name|JAVA_VERSION
argument_list|)
decl_stmt|;
specifier|final
name|Scanner
name|scannerRequiredVersion
init|=
operator|new
name|Scanner
argument_list|(
name|version
argument_list|)
decl_stmt|;
name|scannerRunningVersion
operator|.
name|useDelimiter
argument_list|(
name|DELIMITER
argument_list|)
expr_stmt|;
name|scannerRequiredVersion
operator|.
name|useDelimiter
argument_list|(
name|DELIMITER
argument_list|)
expr_stmt|;
while|while
condition|(
name|scannerRunningVersion
operator|.
name|hasNextInt
argument_list|()
operator|&&
name|scannerRequiredVersion
operator|.
name|hasNextInt
argument_list|()
condition|)
block|{
specifier|final
name|int
name|running
init|=
name|scannerRunningVersion
operator|.
name|nextInt
argument_list|()
decl_stmt|;
specifier|final
name|int
name|required
init|=
name|scannerRequiredVersion
operator|.
name|nextInt
argument_list|()
decl_stmt|;
if|if
condition|(
name|running
operator|==
name|required
condition|)
continue|continue;
return|return
name|running
operator|>=
name|required
return|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|getJavaVersion ()
specifier|public
name|String
name|getJavaVersion
parameter_list|()
block|{
return|return
name|JAVA_VERSION
return|;
block|}
DECL|method|getJavaInstallationDirectory ()
specifier|public
name|String
name|getJavaInstallationDirectory
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.home"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

