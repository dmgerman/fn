begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.search
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
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
name|List
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|TextFlow
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
name|search
operator|.
name|rules
operator|.
name|describer
operator|.
name|ContainsAndRegexBasedSearchRuleDescriber
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
name|util
operator|.
name|TooltipTextUtil
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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_class
DECL|class|ContainsAndRegexBasedSearchRuleDescriberTest
specifier|public
class|class
name|ContainsAndRegexBasedSearchRuleDescriberTest
block|{
annotation|@
name|Test
DECL|method|testNoAst ()
specifier|public
name|void
name|testNoAst
parameter_list|()
block|{
name|String
name|query
init|=
literal|"a b"
decl_stmt|;
name|List
argument_list|<
name|Text
argument_list|>
name|expectedTexts
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"This search contains entries in which any field contains the term "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"a"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|" and "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"b"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|" (case insensitive). "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"\n\nHint: To search specific fields only, enter for example:\n"
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"author=smith and title=electrical"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|MONOSPACED
argument_list|)
argument_list|)
decl_stmt|;
name|TextFlow
name|description
init|=
operator|new
name|ContainsAndRegexBasedSearchRuleDescriber
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
name|query
argument_list|)
operator|.
name|getDescription
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|TextFlowEqualityHelper
operator|.
name|checkIfDescriptionEqualsExpectedTexts
argument_list|(
name|description
argument_list|,
name|expectedTexts
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNoAstRegex ()
specifier|public
name|void
name|testNoAstRegex
parameter_list|()
block|{
name|String
name|query
init|=
literal|"a b"
decl_stmt|;
name|List
argument_list|<
name|Text
argument_list|>
name|expectedTexts
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"This search contains entries in which any field contains the regular expression "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"a"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|" and "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"b"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|" (case insensitive). "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"\n\nHint: To search specific fields only, enter for example:\n"
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"author=smith and title=electrical"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|MONOSPACED
argument_list|)
argument_list|)
decl_stmt|;
name|TextFlow
name|description
init|=
operator|new
name|ContainsAndRegexBasedSearchRuleDescriber
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|query
argument_list|)
operator|.
name|getDescription
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|TextFlowEqualityHelper
operator|.
name|checkIfDescriptionEqualsExpectedTexts
argument_list|(
name|description
argument_list|,
name|expectedTexts
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNoAstRegexCaseSensitive ()
specifier|public
name|void
name|testNoAstRegexCaseSensitive
parameter_list|()
block|{
name|String
name|query
init|=
literal|"a b"
decl_stmt|;
name|List
argument_list|<
name|Text
argument_list|>
name|expectedTexts
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"This search contains entries in which any field contains the regular expression "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"a"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|" and "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"b"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|" (case sensitive). "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"\n\nHint: To search specific fields only, enter for example:\n"
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"author=smith and title=electrical"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|MONOSPACED
argument_list|)
argument_list|)
decl_stmt|;
name|TextFlow
name|description
init|=
operator|new
name|ContainsAndRegexBasedSearchRuleDescriber
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|,
name|query
argument_list|)
operator|.
name|getDescription
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|TextFlowEqualityHelper
operator|.
name|checkIfDescriptionEqualsExpectedTexts
argument_list|(
name|description
argument_list|,
name|expectedTexts
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNoAstCaseSensitive ()
specifier|public
name|void
name|testNoAstCaseSensitive
parameter_list|()
block|{
name|String
name|query
init|=
literal|"a b"
decl_stmt|;
name|List
argument_list|<
name|Text
argument_list|>
name|expectedTexts
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"This search contains entries in which any field contains the term "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"a"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|" and "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"b"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|" (case sensitive). "
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"\n\nHint: To search specific fields only, enter for example:\n"
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
literal|"author=smith and title=electrical"
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|MONOSPACED
argument_list|)
argument_list|)
decl_stmt|;
name|TextFlow
name|description
init|=
operator|new
name|ContainsAndRegexBasedSearchRuleDescriber
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|,
name|query
argument_list|)
operator|.
name|getDescription
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|TextFlowEqualityHelper
operator|.
name|checkIfDescriptionEqualsExpectedTexts
argument_list|(
name|description
argument_list|,
name|expectedTexts
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
