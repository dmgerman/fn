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
DECL|class|DirectoryDialogConfiguration
specifier|public
class|class
name|DirectoryDialogConfiguration
block|{
DECL|field|initialDirectory
specifier|private
specifier|final
name|Path
name|initialDirectory
decl_stmt|;
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
DECL|method|DirectoryDialogConfiguration (Path initialDirectory)
specifier|private
name|DirectoryDialogConfiguration
parameter_list|(
name|Path
name|initialDirectory
parameter_list|)
block|{
name|this
operator|.
name|initialDirectory
operator|=
name|initialDirectory
expr_stmt|;
block|}
DECL|class|Builder
specifier|public
specifier|static
class|class
name|Builder
block|{
DECL|field|initialDirectory
specifier|private
name|Path
name|initialDirectory
decl_stmt|;
DECL|method|build ()
specifier|public
name|DirectoryDialogConfiguration
name|build
parameter_list|()
block|{
return|return
operator|new
name|DirectoryDialogConfiguration
argument_list|(
name|initialDirectory
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
return|return
name|this
return|;
block|}
block|}
block|}
end_class

end_unit

