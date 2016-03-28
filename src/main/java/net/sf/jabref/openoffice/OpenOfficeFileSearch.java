begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.openoffice
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|openoffice
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|List
import|;
end_import

begin_class
DECL|class|OpenOfficeFileSearch
specifier|public
class|class
name|OpenOfficeFileSearch
block|{
DECL|field|fileSearchCancelled
specifier|private
name|boolean
name|fileSearchCancelled
decl_stmt|;
comment|/**      * Search for Program files directory.      * @return the File pointing to the Program files directory, or null if not found.      *   Since we are not including a library for Windows integration, this method can't      *   find the Program files dir in localized Windows installations.      */
DECL|method|findWindowsProgramFilesDir ()
specifier|public
name|List
argument_list|<
name|File
argument_list|>
name|findWindowsProgramFilesDir
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|sourceList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|dirList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Check default 64-bits program directory
name|String
name|progFiles
init|=
name|System
operator|.
name|getenv
argument_list|(
literal|"ProgramFiles"
argument_list|)
decl_stmt|;
if|if
condition|(
name|progFiles
operator|!=
literal|null
condition|)
block|{
name|sourceList
operator|.
name|add
argument_list|(
name|progFiles
argument_list|)
expr_stmt|;
block|}
comment|// Check default 64-bits program directory
name|progFiles
operator|=
name|System
operator|.
name|getenv
argument_list|(
literal|"ProgramFiles(x86)"
argument_list|)
expr_stmt|;
if|if
condition|(
name|progFiles
operator|!=
literal|null
condition|)
block|{
name|sourceList
operator|.
name|add
argument_list|(
name|progFiles
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|rootPath
range|:
name|sourceList
control|)
block|{
name|File
name|root
init|=
operator|new
name|File
argument_list|(
name|rootPath
argument_list|)
decl_stmt|;
name|File
index|[]
name|dirs
init|=
name|root
operator|.
name|listFiles
argument_list|(
name|File
operator|::
name|isDirectory
argument_list|)
decl_stmt|;
if|if
condition|(
name|dirs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|File
name|dir
range|:
name|dirs
control|)
block|{
if|if
condition|(
name|dir
operator|.
name|getPath
argument_list|()
operator|.
name|contains
argument_list|(
literal|"OpenOffice"
argument_list|)
operator|||
name|dir
operator|.
name|getPath
argument_list|()
operator|.
name|contains
argument_list|(
literal|"LibreOffice"
argument_list|)
condition|)
block|{
name|dirList
operator|.
name|add
argument_list|(
name|dir
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|dirList
return|;
block|}
comment|/**      * Search for Program files directory.      * @return the File pointing to the Program files directory, or null if not found.      *   Since we are not including a library for Windows integration, this method can't      *   find the Program files dir in localized Windows installations.      */
DECL|method|findOSXProgramFilesDir ()
specifier|public
name|List
argument_list|<
name|File
argument_list|>
name|findOSXProgramFilesDir
parameter_list|()
block|{
name|List
argument_list|<
name|File
argument_list|>
name|dirList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|File
name|rootDir
init|=
operator|new
name|File
argument_list|(
literal|"/Applications"
argument_list|)
decl_stmt|;
name|File
index|[]
name|files
init|=
name|rootDir
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|files
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|File
name|file
range|:
name|files
control|)
block|{
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
operator|&&
operator|(
literal|"OpenOffice.org.app"
operator|.
name|equals
argument_list|(
name|file
operator|.
name|getName
argument_list|()
argument_list|)
operator|||
literal|"LibreOffice.app"
operator|.
name|equals
argument_list|(
name|file
operator|.
name|getName
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|dirList
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|dirList
return|;
block|}
DECL|method|resetFileSearch ()
specifier|public
name|void
name|resetFileSearch
parameter_list|()
block|{
name|fileSearchCancelled
operator|=
literal|false
expr_stmt|;
block|}
DECL|method|cancelFileSearch ()
specifier|public
name|void
name|cancelFileSearch
parameter_list|()
block|{
name|fileSearchCancelled
operator|=
literal|true
expr_stmt|;
block|}
DECL|method|findFileInDirs (List<File> dirList, String filename)
specifier|public
name|List
argument_list|<
name|File
argument_list|>
name|findFileInDirs
parameter_list|(
name|List
argument_list|<
name|File
argument_list|>
name|dirList
parameter_list|,
name|String
name|filename
parameter_list|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|sofficeFiles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|dir
range|:
name|dirList
control|)
block|{
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
break|break;
block|}
name|File
name|sOffice
init|=
name|findFileInDir
argument_list|(
name|dir
argument_list|,
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
name|sOffice
operator|!=
literal|null
condition|)
block|{
name|sofficeFiles
operator|.
name|add
argument_list|(
name|sOffice
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sofficeFiles
return|;
block|}
comment|/**     * Search for a file, starting at the given directory.     * @param startDir The starting point.     * @param filename The name of the file to search for.     * @return The directory where the file was first found, or null if not found.     */
DECL|method|findFileInDir (File startDir, String filename)
specifier|public
name|File
name|findFileInDir
parameter_list|(
name|File
name|startDir
parameter_list|,
name|String
name|filename
parameter_list|)
block|{
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
return|return
literal|null
return|;
block|}
name|File
index|[]
name|files
init|=
name|startDir
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|files
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|File
name|result
init|=
literal|null
decl_stmt|;
for|for
control|(
name|File
name|file
range|:
name|files
control|)
block|{
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|result
operator|=
name|findFileInDir
argument_list|(
name|file
argument_list|,
name|filename
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
break|break;
block|}
block|}
elseif|else
if|if
condition|(
name|file
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|filename
argument_list|)
condition|)
block|{
name|result
operator|=
name|startDir
expr_stmt|;
break|break;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

