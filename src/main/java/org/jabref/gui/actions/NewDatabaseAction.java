begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
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
name|JabRefFrame
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
name|Defaults
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
name|BibDatabaseMode
import|;
end_import

begin_comment
comment|/**  * The action concerned with opening a new database.  */
end_comment

begin_class
DECL|class|NewDatabaseAction
specifier|public
class|class
name|NewDatabaseAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|jabRefFrame
specifier|private
specifier|final
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
DECL|field|mode
specifier|private
specifier|final
name|BibDatabaseMode
name|mode
decl_stmt|;
DECL|method|NewDatabaseAction (JabRefFrame jabRefFrame, BibDatabaseMode mode)
specifier|public
name|NewDatabaseAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
name|super
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|NEW
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"New %0 library"
argument_list|,
name|mode
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New %0 library"
argument_list|,
name|mode
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
comment|// Create a new, empty, database.
name|BibDatabaseContext
name|bibDatabaseContext
init|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|Defaults
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
decl_stmt|;
name|bibDatabaseContext
operator|.
name|setMode
argument_list|(
name|mode
argument_list|)
expr_stmt|;
name|jabRefFrame
operator|.
name|addTab
argument_list|(
name|bibDatabaseContext
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|jabRefFrame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"New %0 library created."
argument_list|,
name|mode
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
