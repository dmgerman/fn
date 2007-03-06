begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|ExternalFileType
import|;
end_import

begin_comment
comment|/**  * This class represents a file link for a Bibtex entry. */
end_comment

begin_class
DECL|class|FileListEntry
specifier|public
class|class
name|FileListEntry
block|{
DECL|field|link
specifier|private
name|String
name|link
decl_stmt|;
DECL|field|description
specifier|private
name|String
name|description
decl_stmt|;
DECL|field|type
specifier|private
name|ExternalFileType
name|type
decl_stmt|;
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
name|link
operator|=
name|link
expr_stmt|;
name|this
operator|.
name|description
operator|=
name|description
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
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
name|ExternalFileType
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
DECL|method|setType (ExternalFileType type)
specifier|public
name|void
name|setType
parameter_list|(
name|ExternalFileType
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
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|description
operator|+
literal|" : "
operator|+
name|link
operator|+
literal|" : "
operator|+
name|type
return|;
block|}
block|}
end_class

end_unit

