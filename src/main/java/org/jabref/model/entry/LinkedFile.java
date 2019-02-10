begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|InvalidPathException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|Observable
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleStringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|StringProperty
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|FilePreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
operator|.
name|FileHelper
import|;
end_import

begin_comment
comment|/**  * Represents the link to an external file (e.g. associated PDF file).  * This class is {@link Serializable} which is needed for drag and drop in gui  */
end_comment

begin_class
DECL|class|LinkedFile
specifier|public
class|class
name|LinkedFile
implements|implements
name|Serializable
block|{
DECL|field|NULL_OBJECT
specifier|private
specifier|static
specifier|final
name|LinkedFile
name|NULL_OBJECT
init|=
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|//We have to mark these properties as transient because they can't be serialized directly
DECL|field|description
specifier|private
specifier|transient
name|StringProperty
name|description
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|link
specifier|private
specifier|transient
name|StringProperty
name|link
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|fileType
specifier|private
specifier|transient
name|StringProperty
name|fileType
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|method|LinkedFile (String description, Path link, String fileType)
specifier|public
name|LinkedFile
parameter_list|(
name|String
name|description
parameter_list|,
name|Path
name|link
parameter_list|,
name|String
name|fileType
parameter_list|)
block|{
name|this
argument_list|(
name|description
argument_list|,
name|link
operator|.
name|toString
argument_list|()
argument_list|,
name|fileType
argument_list|)
expr_stmt|;
block|}
comment|/**      * @deprecated use the other constructor {@link #LinkedFile(String, Path, String)}      */
annotation|@
name|Deprecated
DECL|method|LinkedFile (String description, String link, String fileType)
specifier|public
name|LinkedFile
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|link
parameter_list|,
name|String
name|fileType
parameter_list|)
block|{
name|this
operator|.
name|description
operator|.
name|setValue
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|description
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|fileType
operator|.
name|setValue
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fileType
argument_list|)
argument_list|)
expr_stmt|;
name|setLink
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|link
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|LinkedFile (URL link, String fileType)
specifier|public
name|LinkedFile
parameter_list|(
name|URL
name|link
parameter_list|,
name|String
name|fileType
parameter_list|)
block|{
name|this
argument_list|(
literal|""
argument_list|,
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|link
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
name|fileType
argument_list|)
expr_stmt|;
block|}
DECL|method|descriptionProperty ()
specifier|public
name|StringProperty
name|descriptionProperty
parameter_list|()
block|{
return|return
name|description
return|;
block|}
DECL|method|linkProperty ()
specifier|public
name|StringProperty
name|linkProperty
parameter_list|()
block|{
return|return
name|link
return|;
block|}
DECL|method|fileTypeProperty ()
specifier|public
name|StringProperty
name|fileTypeProperty
parameter_list|()
block|{
return|return
name|fileType
return|;
block|}
DECL|method|getFileType ()
specifier|public
name|String
name|getFileType
parameter_list|()
block|{
return|return
name|fileType
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setFileType (String fileType)
specifier|public
name|void
name|setFileType
parameter_list|(
name|String
name|fileType
parameter_list|)
block|{
name|this
operator|.
name|fileType
operator|.
name|setValue
argument_list|(
name|fileType
argument_list|)
expr_stmt|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setDescription (String description)
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|this
operator|.
name|description
operator|.
name|setValue
argument_list|(
name|description
argument_list|)
expr_stmt|;
block|}
DECL|method|getLink ()
specifier|public
name|String
name|getLink
parameter_list|()
block|{
return|return
name|link
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setLink (String link)
specifier|public
name|void
name|setLink
parameter_list|(
name|String
name|link
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isOnlineLink
argument_list|(
name|link
argument_list|)
condition|)
block|{
name|this
operator|.
name|link
operator|.
name|setValue
argument_list|(
name|link
operator|.
name|replace
argument_list|(
literal|"\\"
argument_list|,
literal|"/"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|link
operator|.
name|setValue
argument_list|(
name|link
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getObservables ()
specifier|public
name|Observable
index|[]
name|getObservables
parameter_list|()
block|{
return|return
operator|new
name|Observable
index|[]
block|{
name|this
operator|.
name|link
block|,
name|this
operator|.
name|description
block|,
name|this
operator|.
name|fileType
block|}
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|o
operator|instanceof
name|LinkedFile
condition|)
block|{
name|LinkedFile
name|that
init|=
operator|(
name|LinkedFile
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|description
operator|.
name|get
argument_list|()
argument_list|,
name|that
operator|.
name|description
operator|.
name|get
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|link
operator|.
name|get
argument_list|()
argument_list|,
name|that
operator|.
name|link
operator|.
name|get
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|fileType
operator|.
name|get
argument_list|()
argument_list|,
name|that
operator|.
name|fileType
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Writes serialized object to ObjectOutputStream, automatically called      * @param out {@link ObjectOutputStream}      * @throws IOException      */
DECL|method|writeObject (ObjectOutputStream out)
specifier|private
name|void
name|writeObject
parameter_list|(
name|ObjectOutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|writeUTF
argument_list|(
name|getFileType
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeUTF
argument_list|(
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeUTF
argument_list|(
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
comment|/**      * Reads serialized object from ObjectInputStreamm, automatically called      * @param in {@link ObjectInputStream}      * @throws IOException      */
DECL|method|readObject (ObjectInputStream in)
specifier|private
name|void
name|readObject
parameter_list|(
name|ObjectInputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|fileType
operator|=
operator|new
name|SimpleStringProperty
argument_list|(
name|in
operator|.
name|readUTF
argument_list|()
argument_list|)
expr_stmt|;
name|link
operator|=
operator|new
name|SimpleStringProperty
argument_list|(
name|in
operator|.
name|readUTF
argument_list|()
argument_list|)
expr_stmt|;
name|description
operator|=
operator|new
name|SimpleStringProperty
argument_list|(
name|in
operator|.
name|readUTF
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks if the given String is an online link      * @param toCheck The String to check      * @return True if it starts with http://, https:// or contains www; false otherwise      */
DECL|method|isOnlineLink (String toCheck)
specifier|private
name|boolean
name|isOnlineLink
parameter_list|(
name|String
name|toCheck
parameter_list|)
block|{
return|return
name|toCheck
operator|.
name|startsWith
argument_list|(
literal|"http://"
argument_list|)
operator|||
name|toCheck
operator|.
name|startsWith
argument_list|(
literal|"https://"
argument_list|)
operator|||
name|toCheck
operator|.
name|contains
argument_list|(
literal|"www."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|description
operator|.
name|get
argument_list|()
argument_list|,
name|link
operator|.
name|get
argument_list|()
argument_list|,
name|fileType
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"ParsedFileField{"
operator|+
literal|"description='"
operator|+
name|description
operator|.
name|get
argument_list|()
operator|+
literal|'\''
operator|+
literal|", link='"
operator|+
name|link
operator|.
name|get
argument_list|()
operator|+
literal|'\''
operator|+
literal|", fileType='"
operator|+
name|fileType
operator|.
name|get
argument_list|()
operator|+
literal|'\''
operator|+
literal|'}'
return|;
block|}
DECL|method|isEmpty ()
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|NULL_OBJECT
operator|.
name|equals
argument_list|(
name|this
argument_list|)
return|;
block|}
DECL|method|isOnlineLink ()
specifier|public
name|boolean
name|isOnlineLink
parameter_list|()
block|{
return|return
name|isOnlineLink
argument_list|(
name|link
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
DECL|method|findIn (BibDatabaseContext databaseContext, FilePreferences filePreferences)
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|findIn
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|FilePreferences
name|filePreferences
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|dirs
init|=
name|databaseContext
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|filePreferences
argument_list|)
decl_stmt|;
return|return
name|findIn
argument_list|(
name|dirs
argument_list|)
return|;
block|}
comment|/**      * Tries to find the file in the given directories and returns the path to the file (if found). Returns an empty      * optional if the file cannot be found.      */
DECL|method|findIn (List<Path> directories)
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|findIn
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|directories
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|link
operator|.
name|get
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// We do not want to match empty paths (which could be any file or none ?!)
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|link
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|isAbsolute
argument_list|()
operator|||
name|directories
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|file
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|file
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
else|else
block|{
return|return
name|FileHelper
operator|.
name|expandFilenameAsPath
argument_list|(
name|link
operator|.
name|get
argument_list|()
argument_list|,
name|directories
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|InvalidPathException
name|ex
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

