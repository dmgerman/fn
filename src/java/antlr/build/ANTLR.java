begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.build
package|package
name|antlr
operator|.
name|build
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_comment
comment|/** Simple class that uses build.Tool to compile ANTLR's Java stuff */
end_comment

begin_class
DECL|class|ANTLR
specifier|public
class|class
name|ANTLR
block|{
DECL|field|compiler
specifier|public
specifier|static
name|String
name|compiler
init|=
literal|"javac"
decl_stmt|;
DECL|field|jarName
specifier|public
specifier|static
name|String
name|jarName
init|=
literal|"antlr.jar"
decl_stmt|;
DECL|field|root
specifier|public
specifier|static
name|String
name|root
init|=
literal|"."
decl_stmt|;
DECL|field|srcdir
specifier|public
specifier|static
name|String
index|[]
name|srcdir
init|=
block|{
literal|"antlr"
block|,
literal|"antlr/actions/cpp"
block|,
literal|"antlr/actions/java"
block|,
literal|"antlr/actions/csharp"
block|,
literal|"antlr/collections"
block|,
literal|"antlr/collections/impl"
block|,
literal|"antlr/debug"
block|,
literal|"antlr/debug/misc"
block|,
literal|"antlr/preprocessor"
block|}
decl_stmt|;
DECL|method|ANTLR ()
specifier|public
name|ANTLR
parameter_list|()
block|{
name|compiler
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"antlr.build.compiler"
argument_list|,
name|compiler
argument_list|)
expr_stmt|;
name|root
operator|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"antlr.build.root"
argument_list|,
name|root
argument_list|)
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"ANTLR"
return|;
block|}
comment|/** Build ANTLR.  action on cmd-line matches method name */
DECL|method|build (Tool tool)
specifier|public
name|void
name|build
parameter_list|(
name|Tool
name|tool
parameter_list|)
block|{
if|if
condition|(
operator|!
name|rootIsValidANTLRDir
argument_list|(
name|tool
argument_list|)
condition|)
block|{
return|return;
block|}
comment|// run ANTLR on its own .g files
name|tool
operator|.
name|antlr
argument_list|(
name|root
operator|+
literal|"/antlr/antlr.g"
argument_list|)
expr_stmt|;
name|tool
operator|.
name|antlr
argument_list|(
name|root
operator|+
literal|"/antlr/tokdef.g"
argument_list|)
expr_stmt|;
name|tool
operator|.
name|antlr
argument_list|(
name|root
operator|+
literal|"/antlr/preprocessor/preproc.g"
argument_list|)
expr_stmt|;
name|tool
operator|.
name|antlr
argument_list|(
name|root
operator|+
literal|"/antlr/actions/java/action.g"
argument_list|)
expr_stmt|;
name|tool
operator|.
name|antlr
argument_list|(
name|root
operator|+
literal|"/antlr/actions/cpp/action.g"
argument_list|)
expr_stmt|;
name|tool
operator|.
name|antlr
argument_list|(
name|root
operator|+
literal|"/antlr/actions/csharp/action.g"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|srcdir
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|cmd
init|=
name|compiler
operator|+
literal|" -d "
operator|+
name|root
operator|+
literal|" "
operator|+
name|root
operator|+
literal|"/"
operator|+
name|srcdir
index|[
name|i
index|]
operator|+
literal|"/*.java"
decl_stmt|;
name|tool
operator|.
name|system
argument_list|(
name|cmd
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Jar up all the .class files */
DECL|method|jar (Tool tool)
specifier|public
name|void
name|jar
parameter_list|(
name|Tool
name|tool
parameter_list|)
block|{
if|if
condition|(
operator|!
name|rootIsValidANTLRDir
argument_list|(
name|tool
argument_list|)
condition|)
block|{
return|return;
block|}
name|StringBuffer
name|cmd
init|=
operator|new
name|StringBuffer
argument_list|(
literal|2000
argument_list|)
decl_stmt|;
name|cmd
operator|.
name|append
argument_list|(
literal|"jar cvf "
operator|+
name|root
operator|+
literal|"/"
operator|+
name|jarName
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|srcdir
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|cmd
operator|.
name|append
argument_list|(
literal|" "
operator|+
name|root
operator|+
literal|"/"
operator|+
name|srcdir
index|[
name|i
index|]
operator|+
literal|"/*.class"
argument_list|)
expr_stmt|;
block|}
name|tool
operator|.
name|system
argument_list|(
name|cmd
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/** ANTLR root dir must contain an "antlr" dir and must have java      *  files underneath etc...      */
DECL|method|rootIsValidANTLRDir (Tool tool)
specifier|protected
name|boolean
name|rootIsValidANTLRDir
parameter_list|(
name|Tool
name|tool
parameter_list|)
block|{
if|if
condition|(
name|root
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
name|File
name|antlrRootDir
init|=
operator|new
name|File
argument_list|(
name|root
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|antlrRootDir
operator|.
name|exists
argument_list|()
condition|)
block|{
name|tool
operator|.
name|error
argument_list|(
literal|"Property antlr.build.root=="
operator|+
name|root
operator|+
literal|" does not exist"
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|antlrRootDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|tool
operator|.
name|error
argument_list|(
literal|"Property antlr.build.root=="
operator|+
name|root
operator|+
literal|" is not a directory"
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|String
index|[]
name|antlrDir
init|=
name|antlrRootDir
operator|.
name|list
argument_list|(
operator|new
name|FilenameFilter
argument_list|()
block|{
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|dir
parameter_list|,
name|String
name|name
parameter_list|)
block|{
return|return
name|dir
operator|.
name|isDirectory
argument_list|()
operator|&&
name|name
operator|.
name|equals
argument_list|(
literal|"antlr"
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
if|if
condition|(
name|antlrDir
operator|==
literal|null
operator|||
name|antlrDir
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|tool
operator|.
name|error
argument_list|(
literal|"Property antlr.build.root=="
operator|+
name|root
operator|+
literal|" does not appear to be a valid ANTLR project root (no antlr subdir)"
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|File
name|antlrPackageDir
init|=
operator|new
name|File
argument_list|(
name|root
operator|+
literal|"/antlr"
argument_list|)
decl_stmt|;
name|String
index|[]
name|antlrPackageJavaFiles
init|=
name|antlrPackageDir
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|antlrPackageJavaFiles
operator|==
literal|null
operator|||
name|antlrPackageJavaFiles
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|tool
operator|.
name|error
argument_list|(
literal|"Property antlr.build.root=="
operator|+
name|root
operator|+
literal|" does not appear to be a valid ANTLR project root (no .java files in antlr subdir"
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

