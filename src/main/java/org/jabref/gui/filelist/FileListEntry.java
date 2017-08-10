begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.filelist
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|filelist
package|;
end_package

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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
operator|.
name|ExternalFileType
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
name|entry
operator|.
name|LinkedFile
import|;
end_import

begin_comment
comment|/**  * This class represents a file link for a Bibtex entry.  */
end_comment

begin_class
DECL|class|FileListEntry
specifier|public
class|class
name|FileListEntry
block|{
DECL|field|description
specifier|private
name|String
name|description
decl_stmt|;
DECL|field|link
specifier|private
name|String
name|link
decl_stmt|;
DECL|field|type
specifier|private
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|type
decl_stmt|;
DECL|method|FileListEntry (String description, String link)
specifier|public
name|FileListEntry
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|link
parameter_list|)
block|{
name|this
argument_list|(
name|description
argument_list|,
name|link
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|FileListEntry (String description, String link, ExternalFileType type)
specifier|public
name|FileListEntry
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|link
parameter_list|,
name|ExternalFileType
name|type
parameter_list|)
block|{
name|this
operator|.
name|setDescription
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
name|this
operator|.
name|setType
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|type
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|FileListEntry (String description, String link, Optional<ExternalFileType> type)
specifier|public
name|FileListEntry
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|link
parameter_list|,
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|type
parameter_list|)
block|{
name|this
operator|.
name|setDescription
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
name|this
operator|.
name|setType
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getStringArrayRepresentation ()
specifier|public
name|String
index|[]
name|getStringArrayRepresentation
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
name|getDescription
argument_list|()
block|,
name|getLink
argument_list|()
block|,
name|getTypeName
argument_list|()
block|}
return|;
block|}
DECL|method|getTypeName ()
specifier|private
name|String
name|getTypeName
parameter_list|()
block|{
return|return
name|this
operator|.
name|getType
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|?
name|this
operator|.
name|getType
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|""
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
name|getDescription
argument_list|()
operator|+
literal|" : "
operator|+
name|getLink
argument_list|()
operator|+
literal|" : "
operator|+
name|getType
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
return|;
block|}
DECL|method|toParsedFileField ()
specifier|public
name|LinkedFile
name|toParsedFileField
parameter_list|()
block|{
return|return
operator|new
name|LinkedFile
argument_list|(
name|getDescription
argument_list|()
argument_list|,
name|getLink
argument_list|()
argument_list|,
name|getType
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|?
name|getType
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|""
argument_list|)
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
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
operator|=
name|description
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
name|this
operator|.
name|link
operator|=
name|link
expr_stmt|;
block|}
DECL|method|getType ()
specifier|public
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
DECL|method|setType (Optional<ExternalFileType> type)
specifier|public
name|void
name|setType
parameter_list|(
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
block|}
end_class

end_unit

