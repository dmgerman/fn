begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|exporter
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
name|javax
operator|.
name|swing
operator|.
name|filechooser
operator|.
name|FileFilter
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
name|logic
operator|.
name|exporter
operator|.
name|IExportFormat
import|;
end_import

begin_comment
comment|/**  * File filter that lets the user choose export format while choosing file to  * export to. Contains a reference to the ExportFormat in question.  */
end_comment

begin_class
DECL|class|ExportFileFilter
specifier|public
class|class
name|ExportFileFilter
extends|extends
name|FileFilter
implements|implements
name|Comparable
argument_list|<
name|ExportFileFilter
argument_list|>
block|{
DECL|field|format
specifier|private
specifier|final
name|IExportFormat
name|format
decl_stmt|;
DECL|field|extension
specifier|private
specifier|final
name|String
name|extension
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|method|ExportFileFilter (IExportFormat format)
specifier|public
name|ExportFileFilter
parameter_list|(
name|IExportFormat
name|format
parameter_list|)
block|{
name|this
operator|.
name|format
operator|=
name|format
expr_stmt|;
name|this
operator|.
name|extension
operator|=
name|format
operator|.
name|getExtension
argument_list|()
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|format
operator|.
name|getDisplayName
argument_list|()
operator|+
literal|" (*"
operator|+
name|extension
operator|+
literal|')'
expr_stmt|;
block|}
DECL|method|getExportFormat ()
specifier|public
name|IExportFormat
name|getExportFormat
parameter_list|()
block|{
return|return
name|format
return|;
block|}
DECL|method|getExtension ()
specifier|public
name|String
name|getExtension
parameter_list|()
block|{
return|return
name|extension
return|;
block|}
annotation|@
name|Override
DECL|method|accept (File file)
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|file
parameter_list|)
block|{
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
name|file
operator|.
name|getPath
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|endsWith
argument_list|(
name|extension
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|name
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (ExportFileFilter o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|ExportFileFilter
name|o
parameter_list|)
block|{
return|return
name|name
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|name
argument_list|)
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
name|ExportFileFilter
condition|)
block|{
return|return
name|name
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|ExportFileFilter
operator|)
name|o
operator|)
operator|.
name|name
argument_list|)
return|;
block|}
return|return
literal|false
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
name|name
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

