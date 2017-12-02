begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
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
name|dbproperties
operator|.
name|DatabasePropertiesDialog
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
name|preftabs
operator|.
name|PreferencesDialog
import|;
end_import

begin_import
import|import
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|core
operator|.
name|GenericTypeMatcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|dependency
operator|.
name|jsr305
operator|.
name|Nonnull
import|;
end_import

begin_import
import|import
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|fixture
operator|.
name|DialogFixture
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|experimental
operator|.
name|categories
operator|.
name|Category
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|finder
operator|.
name|WindowFinder
operator|.
name|findDialog
import|;
end_import

begin_class
annotation|@
name|Category
argument_list|(
name|org
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|GUITest
operator|.
name|class
argument_list|)
DECL|class|GUITest
specifier|public
class|class
name|GUITest
extends|extends
name|AbstractUITest
block|{
annotation|@
name|Test
DECL|method|testExit ()
specifier|public
name|void
name|testExit
parameter_list|()
block|{
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNewFile ()
specifier|public
name|void
name|testNewFile
parameter_list|()
block|{
name|newDatabase
argument_list|()
expr_stmt|;
name|closeDatabase
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCreateBibtexEntry ()
specifier|public
name|void
name|testCreateBibtexEntry
parameter_list|()
throws|throws
name|IOException
block|{
name|newDatabase
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"BibTeX"
argument_list|,
literal|"New entry..."
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|findDialog
argument_list|(
name|EntryTypeDialog
operator|.
name|class
argument_list|)
operator|.
name|withTimeout
argument_list|(
literal|10_000
argument_list|)
operator|.
name|using
argument_list|(
name|robot
argument_list|()
argument_list|)
operator|.
name|button
argument_list|(
operator|new
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
argument_list|(
name|JButton
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
annotation|@
name|Nonnull
name|JButton
name|jButton
parameter_list|)
block|{
return|return
literal|"Book"
operator|.
name|equals
argument_list|(
name|jButton
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|takeScreenshot
argument_list|(
name|mainFrame
argument_list|,
literal|"MainWindowWithOneDatabase"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testOpenAndSavePreferences ()
specifier|public
name|void
name|testOpenAndSavePreferences
parameter_list|()
throws|throws
name|IOException
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Options"
argument_list|,
literal|"Preferences"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|robot
argument_list|()
operator|.
name|waitForIdle
argument_list|()
expr_stmt|;
name|DialogFixture
name|preferencesDialog
init|=
name|findDialog
argument_list|(
name|PreferencesDialog
operator|.
name|class
argument_list|)
operator|.
name|withTimeout
argument_list|(
literal|10_000
argument_list|)
operator|.
name|using
argument_list|(
name|robot
argument_list|()
argument_list|)
decl_stmt|;
name|takeScreenshot
argument_list|(
name|preferencesDialog
argument_list|,
literal|"PreferencesDialog"
argument_list|)
expr_stmt|;
name|preferencesDialog
operator|.
name|button
argument_list|(
operator|new
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
argument_list|(
name|JButton
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
annotation|@
name|Nonnull
name|JButton
name|jButton
parameter_list|)
block|{
return|return
literal|"OK"
operator|.
name|equals
argument_list|(
name|jButton
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
comment|/**      * tests different buttons      * sometimes this test clicks some buttons twice to reverse their effect and leaves JabRef as it was before      */
annotation|@
name|Test
DECL|method|testViewChanges ()
specifier|public
name|void
name|testViewChanges
parameter_list|()
block|{
name|newDatabase
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Increase table font size"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Decrease table font size"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Web search"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Web search"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Toggle groups interface"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Toggle groups interface"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Toggle entry preview"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Toggle entry preview"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Next preview layout"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Previous preview layout"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Hide/show toolbar"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Hide/show toolbar"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"View"
argument_list|,
literal|"Focus entry table"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|closeDatabase
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDatabasePropertiesDialog ()
specifier|public
name|void
name|testDatabasePropertiesDialog
parameter_list|()
throws|throws
name|IOException
block|{
name|newDatabase
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"Library properties"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|robot
argument_list|()
operator|.
name|waitForIdle
argument_list|()
expr_stmt|;
name|DialogFixture
name|databasePropertiesDialog
init|=
name|findDialog
argument_list|(
name|DatabasePropertiesDialog
operator|.
name|class
argument_list|)
operator|.
name|withTimeout
argument_list|(
literal|10_000
argument_list|)
operator|.
name|using
argument_list|(
name|robot
argument_list|()
argument_list|)
decl_stmt|;
name|takeScreenshot
argument_list|(
name|databasePropertiesDialog
argument_list|,
literal|"DatabasePropertiesDialog"
argument_list|)
expr_stmt|;
name|databasePropertiesDialog
operator|.
name|button
argument_list|(
operator|new
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
argument_list|(
name|JButton
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
annotation|@
name|Nonnull
name|JButton
name|jButton
parameter_list|)
block|{
return|return
literal|"OK"
operator|.
name|equals
argument_list|(
name|jButton
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|closeDatabase
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

