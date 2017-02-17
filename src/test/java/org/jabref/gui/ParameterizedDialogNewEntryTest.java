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
name|util
operator|.
name|Arrays
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
name|Locale
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
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|GUITests
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
name|JTableFixture
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
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
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
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
annotation|@
name|Category
argument_list|(
name|GUITests
operator|.
name|class
argument_list|)
DECL|class|ParameterizedDialogNewEntryTest
specifier|public
class|class
name|ParameterizedDialogNewEntryTest
extends|extends
name|AbstractUITest
block|{
DECL|field|databaseMode
specifier|private
specifier|final
name|String
name|databaseMode
decl_stmt|;
DECL|field|entryType
specifier|private
specifier|final
name|String
name|entryType
decl_stmt|;
DECL|method|ParameterizedDialogNewEntryTest (String databaseMode, String entryType)
specifier|public
name|ParameterizedDialogNewEntryTest
parameter_list|(
name|String
name|databaseMode
parameter_list|,
name|String
name|entryType
parameter_list|)
block|{
name|this
operator|.
name|databaseMode
operator|=
name|databaseMode
expr_stmt|;
name|this
operator|.
name|entryType
operator|=
name|entryType
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addEntryOfGivenType ()
specifier|public
name|void
name|addEntryOfGivenType
parameter_list|()
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"New "
operator|+
name|databaseMode
operator|+
literal|" database"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|JTableFixture
name|entryTable
init|=
name|mainFrame
operator|.
name|table
argument_list|()
decl_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|0
argument_list|)
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
name|selectEntryType
argument_list|()
expr_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
DECL|method|selectEntryType ()
specifier|private
name|void
name|selectEntryType
parameter_list|()
block|{
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|matcher
init|=
operator|new
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
argument_list|(
name|JDialog
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
name|JDialog
name|dialog
parameter_list|)
block|{
return|return
literal|"Select entry type"
operator|.
name|equals
argument_list|(
name|dialog
operator|.
name|getTitle
argument_list|()
argument_list|)
return|;
block|}
block|}
decl_stmt|;
name|findDialog
argument_list|(
name|matcher
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
name|entryType
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
block|}
annotation|@
name|Test
DECL|method|addEntryPlainTextOfGivenType ()
specifier|public
name|void
name|addEntryPlainTextOfGivenType
parameter_list|()
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"New "
operator|+
name|databaseMode
operator|+
literal|" database"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|JTableFixture
name|entryTable
init|=
name|mainFrame
operator|.
name|table
argument_list|()
decl_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"BibTeX"
argument_list|,
literal|"New entry from plain text..."
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|selectEntryType
argument_list|()
expr_stmt|;
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|matcher2
init|=
name|plainTextMatcher
argument_list|()
decl_stmt|;
name|findDialog
argument_list|(
name|matcher2
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
literal|"Accept"
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
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|closeAddingEntryPlainTextOfGivenType ()
specifier|public
name|void
name|closeAddingEntryPlainTextOfGivenType
parameter_list|()
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"New "
operator|+
name|databaseMode
operator|+
literal|" database"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|JTableFixture
name|entryTable
init|=
name|mainFrame
operator|.
name|table
argument_list|()
decl_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"BibTeX"
argument_list|,
literal|"New entry from plain text..."
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|selectEntryType
argument_list|()
expr_stmt|;
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|matcher2
init|=
name|plainTextMatcher
argument_list|()
decl_stmt|;
name|findDialog
argument_list|(
name|matcher2
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
name|close
argument_list|()
expr_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cancelAddingEntryPlainTextOfGivenType ()
specifier|public
name|void
name|cancelAddingEntryPlainTextOfGivenType
parameter_list|()
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"New "
operator|+
name|databaseMode
operator|+
literal|" database"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|JTableFixture
name|entryTable
init|=
name|mainFrame
operator|.
name|table
argument_list|()
decl_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"BibTeX"
argument_list|,
literal|"New entry from plain text..."
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|selectEntryType
argument_list|()
expr_stmt|;
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|matcher2
init|=
name|plainTextMatcher
argument_list|()
decl_stmt|;
name|findDialog
argument_list|(
name|matcher2
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
literal|"Cancel"
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
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
DECL|method|plainTextMatcher ()
specifier|private
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|plainTextMatcher
parameter_list|()
block|{
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|matcher2
init|=
operator|new
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
argument_list|(
name|JDialog
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
name|JDialog
name|dialog
parameter_list|)
block|{
return|return
operator|(
literal|"Plain text import for "
operator|+
name|entryType
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|)
operator|.
name|equals
argument_list|(
name|dialog
operator|.
name|getTitle
argument_list|()
argument_list|)
return|;
block|}
block|}
decl_stmt|;
return|return
name|matcher2
return|;
block|}
annotation|@
name|Parameterized
operator|.
name|Parameters
argument_list|(
name|name
operator|=
literal|"{index}: {0} : {1}"
argument_list|)
DECL|method|instancesToTest ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|Object
index|[]
argument_list|>
name|instancesToTest
parameter_list|()
block|{
comment|// Create entry from menu
comment|// Structure:
comment|// {"BibTeX"/"biblatex", "type"}
comment|// @formatter:off
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|Object
index|[]
block|{
literal|"BibTeX"
block|,
literal|"Article"
block|}
argument_list|,
comment|/*                new Object[]{"BibTeX", "InBook"},                 new Object[]{"BibTeX", "Book"},                 new Object[]{"BibTeX", "Booklet"},                 new Object[]{"BibTeX", "InCollection"},                 new Object[]{"BibTeX", "Conference"},                 new Object[]{"BibTeX", "InProceedings"},                 new Object[]{"BibTeX", "Proceedings"},                 new Object[]{"BibTeX", "Manual"},                 new Object[]{"BibTeX", "MastersThesis"},                 new Object[]{"BibTeX", "PhdThesis"},                 new Object[]{"BibTeX", "TechReport"},                 new Object[]{"BibTeX", "Unpublished"},                 new Object[]{"BibTeX", "Misc"},                 new Object[]{"BibTeX", "Electronic"},                 new Object[]{"BibTeX", "IEEEtranBSTCTL"},                 new Object[]{"BibTeX", "Periodical"},                 new Object[]{"BibTeX", "Patent"},                 new Object[]{"BibTeX", "Standard"},                 new Object[]{"biblatex", "Article"},                 new Object[]{"biblatex", "Book"},                 new Object[]{"biblatex", "BookInBook"},                 new Object[]{"biblatex", "Booklet"},                 new Object[]{"biblatex", "Collection"},                 new Object[]{"biblatex", "Conference"},                 new Object[]{"biblatex", "Electronic"},                 new Object[]{"biblatex", "IEEEtranBSTCTL"},                 new Object[]{"biblatex", "InBook"},                 new Object[]{"biblatex", "InCollection"},                 new Object[]{"biblatex", "InProceedings"},                 new Object[]{"biblatex", "InReference"},                 new Object[]{"biblatex", "Manual"},                 new Object[]{"biblatex", "MastersThesis"},                 new Object[]{"biblatex", "Misc"},                 new Object[]{"biblatex", "MvBook"},                 new Object[]{"biblatex", "MvCollection"},                 new Object[]{"biblatex", "MvProceedings"},                 new Object[]{"biblatex", "MvReference"},                 new Object[]{"biblatex", "Online"},                 new Object[]{"biblatex", "Patent"},                 new Object[]{"biblatex", "Periodical"},                 new Object[]{"biblatex", "PhdThesis"},                 new Object[]{"biblatex", "Proceedings"},                 new Object[]{"biblatex", "Reference"},                 new Object[]{"biblatex", "Report"},                 new Object[]{"biblatex", "Set"},                 new Object[]{"biblatex", "SuppBook"},                 new Object[]{"biblatex", "SuppCollection"},                 new Object[]{"biblatex", "SuppPeriodical"},                 new Object[]{"biblatex", "TechReport"},                 new Object[]{"biblatex", "Thesis"},                 new Object[]{"biblatex", "Unpublished"},*/
operator|new
name|Object
index|[]
block|{
literal|"biblatex"
block|,
literal|"WWW"
block|}
argument_list|)
return|;
comment|// @formatter:on
block|}
block|}
end_class

end_unit
