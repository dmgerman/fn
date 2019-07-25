begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|texparser
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
name|java
operator|.
name|util
operator|.
name|StringJoiner
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_class
DECL|class|FileNodeViewModel
specifier|public
class|class
name|FileNodeViewModel
block|{
DECL|field|path
specifier|private
specifier|final
name|Path
name|path
decl_stmt|;
DECL|field|children
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|FileNodeViewModel
argument_list|>
name|children
decl_stmt|;
DECL|field|fileCount
specifier|private
name|int
name|fileCount
decl_stmt|;
DECL|method|FileNodeViewModel (Path path)
specifier|public
name|FileNodeViewModel
parameter_list|(
name|Path
name|path
parameter_list|)
block|{
name|this
operator|.
name|path
operator|=
name|path
expr_stmt|;
name|this
operator|.
name|fileCount
operator|=
literal|0
expr_stmt|;
name|this
operator|.
name|children
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
block|}
DECL|method|getPath ()
specifier|public
name|Path
name|getPath
parameter_list|()
block|{
return|return
name|path
return|;
block|}
DECL|method|getFileCount ()
specifier|public
name|int
name|getFileCount
parameter_list|()
block|{
return|return
name|fileCount
return|;
block|}
DECL|method|setFileCount (int fileCount)
specifier|public
name|void
name|setFileCount
parameter_list|(
name|int
name|fileCount
parameter_list|)
block|{
name|this
operator|.
name|fileCount
operator|=
name|fileCount
expr_stmt|;
block|}
DECL|method|getChildren ()
specifier|public
name|ObservableList
argument_list|<
name|FileNodeViewModel
argument_list|>
name|getChildren
parameter_list|()
block|{
return|return
name|children
return|;
block|}
comment|/**      * Return a string for displaying a node name (and its number of children if it is a directory).      */
DECL|method|getDisplayText ()
specifier|public
name|String
name|getDisplayText
parameter_list|()
block|{
if|if
condition|(
name|path
operator|.
name|toFile
argument_list|()
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s (%s %s)"
argument_list|,
name|path
operator|.
name|getFileName
argument_list|()
argument_list|,
name|fileCount
argument_list|,
name|fileCount
operator|==
literal|1
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"file"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"files"
argument_list|)
argument_list|)
return|;
block|}
return|return
name|path
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
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
operator|new
name|StringJoiner
argument_list|(
literal|", "
argument_list|,
name|FileNodeViewModel
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|"["
argument_list|,
literal|"]"
argument_list|)
operator|.
name|add
argument_list|(
literal|"path="
operator|+
name|path
argument_list|)
operator|.
name|add
argument_list|(
literal|"fileCount="
operator|+
name|fileCount
argument_list|)
operator|.
name|add
argument_list|(
literal|"children="
operator|+
name|children
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

