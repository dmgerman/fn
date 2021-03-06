begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.copyfiles
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|copyfiles
package|;
end_package

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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ObjectProperty
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
name|SimpleObjectProperty
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
name|gui
operator|.
name|icon
operator|.
name|IconTheme
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
name|icon
operator|.
name|JabRefIcon
import|;
end_import

begin_class
DECL|class|CopyFilesResultItemViewModel
specifier|public
class|class
name|CopyFilesResultItemViewModel
block|{
DECL|field|file
specifier|private
specifier|final
name|StringProperty
name|file
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|icon
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|JabRefIcon
argument_list|>
name|icon
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WARNING
argument_list|)
decl_stmt|;
DECL|field|message
specifier|private
specifier|final
name|StringProperty
name|message
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|method|CopyFilesResultItemViewModel (Path file, boolean success, String message)
specifier|public
name|CopyFilesResultItemViewModel
parameter_list|(
name|Path
name|file
parameter_list|,
name|boolean
name|success
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|this
operator|.
name|file
operator|.
name|setValue
argument_list|(
name|file
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|message
operator|.
name|setValue
argument_list|(
name|message
argument_list|)
expr_stmt|;
if|if
condition|(
name|success
condition|)
block|{
name|this
operator|.
name|icon
operator|.
name|setValue
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|CHECK
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getFile ()
specifier|public
name|StringProperty
name|getFile
parameter_list|()
block|{
return|return
name|file
return|;
block|}
DECL|method|getMessage ()
specifier|public
name|StringProperty
name|getMessage
parameter_list|()
block|{
return|return
name|message
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|ObjectProperty
argument_list|<
name|JabRefIcon
argument_list|>
name|getIcon
parameter_list|()
block|{
return|return
name|icon
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
literal|"CopyFilesResultItemViewModel [file="
operator|+
name|file
operator|.
name|get
argument_list|()
operator|+
literal|", message="
operator|+
name|message
operator|.
name|get
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

