begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
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
name|JComboBox
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
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|JTextComponent
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
name|assertj
operator|.
name|swing
operator|.
name|timing
operator|.
name|Condition
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

begin_import
import|import static
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|timing
operator|.
name|Pause
operator|.
name|pause
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
DECL|class|IdFetcherDialogTest
specifier|public
class|class
name|IdFetcherDialogTest
extends|extends
name|AbstractUITest
block|{
DECL|field|databaseMode
DECL|field|fetcherType
DECL|field|fetchID
specifier|private
specifier|final
name|String
name|databaseMode
decl_stmt|,
name|fetcherType
decl_stmt|,
name|fetchID
decl_stmt|;
DECL|method|IdFetcherDialogTest (String databaseMode, String fetcherType, String fetchID)
specifier|public
name|IdFetcherDialogTest
parameter_list|(
name|String
name|databaseMode
parameter_list|,
name|String
name|fetcherType
parameter_list|,
name|String
name|fetchID
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
name|fetcherType
operator|=
name|fetcherType
expr_stmt|;
name|this
operator|.
name|fetchID
operator|=
name|fetchID
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|insertEmptySearchID ()
specifier|public
name|void
name|insertEmptySearchID
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
literal|"Generate"
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
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|matcherEmptyDialog
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
literal|"Empty search ID"
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
name|matcherEmptyDialog
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
DECL|method|testFetcherDialog ()
specifier|public
name|void
name|testFetcherDialog
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
name|comboBox
argument_list|(
operator|new
name|GenericTypeMatcher
argument_list|<
name|JComboBox
argument_list|>
argument_list|(
name|JComboBox
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
name|JComboBox
name|component
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
argument_list|)
operator|.
name|selectItem
argument_list|(
name|fetcherType
argument_list|)
expr_stmt|;
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
name|textBox
argument_list|(
operator|new
name|GenericTypeMatcher
argument_list|<
name|JTextComponent
argument_list|>
argument_list|(
name|JTextComponent
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
name|JTextComponent
name|component
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
argument_list|)
operator|.
name|enterText
argument_list|(
name|fetchID
argument_list|)
expr_stmt|;
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
literal|"Generate"
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
name|pause
argument_list|(
operator|new
name|Condition
argument_list|(
literal|"entrySize"
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|test
parameter_list|()
block|{
return|return
name|entryTable
operator|.
name|rowCount
argument_list|()
operator|==
literal|1
return|;
block|}
block|}
argument_list|,
literal|10_000
argument_list|)
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
name|Parameterized
operator|.
name|Parameters
argument_list|(
name|name
operator|=
literal|"{index}: {0} : {1} : {2}"
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
literal|"DOI"
block|,
literal|"10.1002/9781118257517"
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
literal|"BibLaTeX"
block|,
literal|"DOI"
block|,
literal|"10.1002/9781118257517"
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
literal|"BibTeX"
block|,
literal|"ISBN"
block|,
literal|"9780321356680"
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
literal|"BibLaTeX"
block|,
literal|"ISBN"
block|,
literal|"9780321356680"
block|}
argument_list|)
return|;
block|}
block|}
end_class

end_unit

