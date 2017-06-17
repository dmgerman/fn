begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|FileChooser
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
name|util
operator|.
name|FileExtensions
import|;
end_import

begin_class
DECL|class|FileDialogConfiguration
specifier|public
class|class
name|FileDialogConfiguration
block|{
DECL|field|extensionFilters
specifier|private
specifier|final
name|List
argument_list|<
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|>
name|extensionFilters
decl_stmt|;
DECL|field|initialDirectory
specifier|private
specifier|final
name|Path
name|initialDirectory
decl_stmt|;
DECL|field|defaultExtension
specifier|private
specifier|final
name|FileChooser
operator|.
name|ExtensionFilter
name|defaultExtension
decl_stmt|;
DECL|field|initialFileName
specifier|private
specifier|final
name|String
name|initialFileName
decl_stmt|;
DECL|method|FileDialogConfiguration (Path initialDirectory, List<FileChooser.ExtensionFilter> extensionFilters, FileChooser.ExtensionFilter defaultExtension, String initialFileName)
specifier|private
name|FileDialogConfiguration
parameter_list|(
name|Path
name|initialDirectory
parameter_list|,
name|List
argument_list|<
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|>
name|extensionFilters
parameter_list|,
name|FileChooser
operator|.
name|ExtensionFilter
name|defaultExtension
parameter_list|,
name|String
name|initialFileName
parameter_list|)
block|{
name|this
operator|.
name|initialDirectory
operator|=
name|initialDirectory
expr_stmt|;
name|this
operator|.
name|extensionFilters
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|extensionFilters
argument_list|)
expr_stmt|;
name|this
operator|.
name|defaultExtension
operator|=
name|defaultExtension
expr_stmt|;
name|this
operator|.
name|initialFileName
operator|=
name|initialFileName
expr_stmt|;
block|}
DECL|method|getInitialDirectory ()
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|getInitialDirectory
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|initialDirectory
argument_list|)
return|;
block|}
DECL|method|getDefaultExtension ()
specifier|public
name|FileChooser
operator|.
name|ExtensionFilter
name|getDefaultExtension
parameter_list|()
block|{
return|return
name|defaultExtension
return|;
block|}
DECL|method|getInitialFileName ()
specifier|public
name|String
name|getInitialFileName
parameter_list|()
block|{
return|return
name|initialFileName
return|;
block|}
DECL|method|getExtensionFilters ()
specifier|public
name|List
argument_list|<
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|>
name|getExtensionFilters
parameter_list|()
block|{
return|return
name|extensionFilters
return|;
block|}
DECL|class|Builder
specifier|public
specifier|static
class|class
name|Builder
block|{
DECL|field|extensionFilter
specifier|private
specifier|final
name|List
argument_list|<
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|>
name|extensionFilter
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|initialDirectory
specifier|private
name|Path
name|initialDirectory
decl_stmt|;
DECL|field|defaultExtension
specifier|private
name|FileChooser
operator|.
name|ExtensionFilter
name|defaultExtension
decl_stmt|;
DECL|field|initialFileName
specifier|private
name|String
name|initialFileName
decl_stmt|;
DECL|method|addExtensionFilter (FileExtensions extension)
specifier|public
name|Builder
name|addExtensionFilter
parameter_list|(
name|FileExtensions
name|extension
parameter_list|)
block|{
name|extensionFilter
operator|.
name|add
argument_list|(
name|toFilter
argument_list|(
name|extension
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|toFilter (FileExtensions extension)
specifier|private
name|FileChooser
operator|.
name|ExtensionFilter
name|toFilter
parameter_list|(
name|FileExtensions
name|extension
parameter_list|)
block|{
return|return
operator|new
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|(
name|extension
operator|.
name|getDescription
argument_list|()
argument_list|,
name|extension
operator|.
name|getExtensionsAsList
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|ending
lambda|->
literal|"*."
operator|+
name|ending
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|method|addExtensionFilters (Collection<FileExtensions> extensions)
specifier|public
name|Builder
name|addExtensionFilters
parameter_list|(
name|Collection
argument_list|<
name|FileExtensions
argument_list|>
name|extensions
parameter_list|)
block|{
name|extensions
operator|.
name|forEach
argument_list|(
name|this
operator|::
name|addExtensionFilter
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|build ()
specifier|public
name|FileDialogConfiguration
name|build
parameter_list|()
block|{
return|return
operator|new
name|FileDialogConfiguration
argument_list|(
name|initialDirectory
argument_list|,
name|extensionFilter
argument_list|,
name|defaultExtension
argument_list|,
name|initialFileName
argument_list|)
return|;
block|}
DECL|method|withInitialDirectory (Path directory)
specifier|public
name|Builder
name|withInitialDirectory
parameter_list|(
name|Path
name|directory
parameter_list|)
block|{
if|if
condition|(
name|directory
operator|==
literal|null
condition|)
block|{
comment|//It could be that somehow the path is null, for example if it got deleted in the meantime
name|initialDirectory
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
comment|//Dir must be a folder, not a file
if|if
condition|(
operator|!
name|Files
operator|.
name|isDirectory
argument_list|(
name|directory
argument_list|)
condition|)
block|{
name|directory
operator|=
name|directory
operator|.
name|getParent
argument_list|()
expr_stmt|;
block|}
comment|//The lines above work also if the dir does not exist at all!
comment|//NULL is accepted by the filechooser as no inital path
if|if
condition|(
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|directory
argument_list|)
condition|)
block|{
name|directory
operator|=
literal|null
expr_stmt|;
block|}
name|initialDirectory
operator|=
name|directory
expr_stmt|;
block|}
return|return
name|this
return|;
block|}
DECL|method|withInitialDirectory (String directory)
specifier|public
name|Builder
name|withInitialDirectory
parameter_list|(
name|String
name|directory
parameter_list|)
block|{
if|if
condition|(
name|directory
operator|!=
literal|null
condition|)
block|{
name|withInitialDirectory
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|directory
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|initialDirectory
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|this
return|;
block|}
DECL|method|withDefaultExtension (FileExtensions extension)
specifier|public
name|Builder
name|withDefaultExtension
parameter_list|(
name|FileExtensions
name|extension
parameter_list|)
block|{
name|defaultExtension
operator|=
name|toFilter
argument_list|(
name|extension
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withInitialFileName (String initialFileName)
specifier|public
name|Builder
name|withInitialFileName
parameter_list|(
name|String
name|initialFileName
parameter_list|)
block|{
name|this
operator|.
name|initialFileName
operator|=
name|initialFileName
expr_stmt|;
return|return
name|this
return|;
block|}
block|}
block|}
end_class

end_unit

