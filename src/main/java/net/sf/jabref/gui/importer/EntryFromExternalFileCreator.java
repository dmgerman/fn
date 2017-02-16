begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
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
name|BibEntry
import|;
end_import

begin_comment
comment|/** EntryCreator for any predefined ExternalFileType.  * This Creator accepts all files with the extension defined in the ExternalFileType.  */
end_comment

begin_class
DECL|class|EntryFromExternalFileCreator
specifier|public
class|class
name|EntryFromExternalFileCreator
extends|extends
name|EntryFromFileCreator
block|{
DECL|method|EntryFromExternalFileCreator (ExternalFileType externalFileType)
specifier|public
name|EntryFromExternalFileCreator
parameter_list|(
name|ExternalFileType
name|externalFileType
parameter_list|)
block|{
name|super
argument_list|(
name|externalFileType
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|accept (File f)
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|f
parameter_list|)
block|{
return|return
name|f
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"."
operator|+
name|externalFileType
operator|.
name|getExtension
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|createBibtexEntry (File file)
specifier|protected
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|createBibtexEntry
parameter_list|(
name|File
name|file
parameter_list|)
block|{
if|if
condition|(
operator|!
name|accept
argument_list|(
name|file
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
name|externalFileType
operator|.
name|getName
argument_list|()
return|;
block|}
block|}
end_class

end_unit

