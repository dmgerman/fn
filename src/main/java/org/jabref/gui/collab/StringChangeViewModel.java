begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.collab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|VBox
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableInsertString
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
name|undo
operator|.
name|UndoableStringChange
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
name|database
operator|.
name|KeyCollisionException
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
name|BibtexString
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|StringChangeViewModel
class|class
name|StringChangeViewModel
extends|extends
name|DatabaseChangeViewModel
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|StringChangeViewModel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|string
specifier|private
specifier|final
name|BibtexString
name|string
decl_stmt|;
DECL|field|disk
specifier|private
specifier|final
name|String
name|disk
decl_stmt|;
DECL|field|label
specifier|private
specifier|final
name|String
name|label
decl_stmt|;
DECL|method|StringChangeViewModel (BibtexString string, BibtexString tmpString, String disk)
specifier|public
name|StringChangeViewModel
parameter_list|(
name|BibtexString
name|string
parameter_list|,
name|BibtexString
name|tmpString
parameter_list|,
name|String
name|disk
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified string"
argument_list|)
operator|+
literal|": '"
operator|+
name|tmpString
operator|.
name|getName
argument_list|()
operator|+
literal|'\''
argument_list|)
expr_stmt|;
name|this
operator|.
name|string
operator|=
name|string
expr_stmt|;
name|this
operator|.
name|label
operator|=
name|tmpString
operator|.
name|getName
argument_list|()
expr_stmt|;
name|this
operator|.
name|disk
operator|=
name|disk
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BibDatabaseContext database, NamedCompound undoEdit)
specifier|public
name|void
name|makeChange
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
if|if
condition|(
name|string
operator|==
literal|null
condition|)
block|{
comment|// The string was removed or renamed locally. We guess that it was removed.
name|BibtexString
name|bs
init|=
operator|new
name|BibtexString
argument_list|(
name|label
argument_list|,
name|disk
argument_list|)
decl_stmt|;
try|try
block|{
name|database
operator|.
name|getDatabase
argument_list|()
operator|.
name|addString
argument_list|(
name|bs
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertString
argument_list|(
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|bs
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Error: could not add string '"
operator|+
name|bs
operator|.
name|getName
argument_list|()
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
name|mem
init|=
name|string
operator|.
name|getContent
argument_list|()
decl_stmt|;
name|string
operator|.
name|setContent
argument_list|(
name|disk
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableStringChange
argument_list|(
name|string
argument_list|,
literal|false
argument_list|,
name|mem
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|description ()
specifier|public
name|Node
name|description
parameter_list|()
block|{
name|VBox
name|container
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|Label
name|header
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified string"
argument_list|)
argument_list|)
decl_stmt|;
name|header
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|header
argument_list|,
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Label"
argument_list|)
operator|+
literal|": "
operator|+
name|label
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Content"
argument_list|)
operator|+
literal|": "
operator|+
name|disk
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|string
operator|==
literal|null
condition|)
block|{
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot merge this change"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"The string has been removed locally"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Current content"
argument_list|)
operator|+
literal|": "
operator|+
name|string
operator|.
name|getContent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|container
return|;
block|}
block|}
end_class

end_unit

