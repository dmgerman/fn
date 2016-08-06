begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
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
name|Collections
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
name|Globals
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|ClearFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|HtmlToLatexFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|HtmlToUnicodeFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|LatexCleanupFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeDateFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeMonthFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeNamesFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizePagesFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|OrdinalsToSuperscriptFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|RemoveBracesFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|UnicodeToLatexFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|UnitsToLatexFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|casechanger
operator|.
name|CapitalizeFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|casechanger
operator|.
name|LowerCaseFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|casechanger
operator|.
name|ProtectTermsFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|casechanger
operator|.
name|SentenceCaseFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|casechanger
operator|.
name|TitleCaseFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|casechanger
operator|.
name|UpperCaseFormatter
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
name|logic
operator|.
name|formatter
operator|.
name|minifier
operator|.
name|MinifyNameListFormatter
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
name|logic
operator|.
name|layout
operator|.
name|format
operator|.
name|LatexToUnicodeFormatter
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
name|logic
operator|.
name|protectedterms
operator|.
name|ProtectedTermsLoader
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
name|logic
operator|.
name|protectedterms
operator|.
name|ProtectedTermsPreferences
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
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
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
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
name|assertFalse
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
name|assertNotEquals
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
name|assertNotNull
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
DECL|class|FormatterTest
specifier|public
class|class
name|FormatterTest
block|{
DECL|field|formatter
specifier|public
name|Formatter
name|formatter
decl_stmt|;
DECL|method|FormatterTest (Formatter formatter)
specifier|public
name|FormatterTest
parameter_list|(
name|Formatter
name|formatter
parameter_list|)
block|{
name|this
operator|.
name|formatter
operator|=
name|formatter
expr_stmt|;
block|}
annotation|@
name|BeforeClass
DECL|method|setUp ()
specifier|public
specifier|static
name|void
name|setUp
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|ProtectTermsFormatter
operator|.
name|setProtectedTermsLoader
argument_list|(
operator|new
name|ProtectedTermsLoader
argument_list|(
operator|new
name|ProtectedTermsPreferences
argument_list|(
name|ProtectedTermsLoader
operator|.
name|getInternalLists
argument_list|()
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getNameReturnsNotNull ()
specifier|public
name|void
name|getNameReturnsNotNull
parameter_list|()
block|{
name|assertNotNull
argument_list|(
name|formatter
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getNameReturnsNotEmpty ()
specifier|public
name|void
name|getNameReturnsNotEmpty
parameter_list|()
block|{
name|assertNotEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getKeyReturnsNotNull ()
specifier|public
name|void
name|getKeyReturnsNotNull
parameter_list|()
block|{
name|assertNotNull
argument_list|(
name|formatter
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getKeyReturnsNotEmpty ()
specifier|public
name|void
name|getKeyReturnsNotEmpty
parameter_list|()
block|{
name|assertNotEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|formatOfNullThrowsException ()
specifier|public
name|void
name|formatOfNullThrowsException
parameter_list|()
block|{
name|formatter
operator|.
name|format
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatOfEmptyStringReturnsEmpty ()
specifier|public
name|void
name|formatOfEmptyStringReturnsEmpty
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatNotReturnsNull ()
specifier|public
name|void
name|formatNotReturnsNull
parameter_list|()
block|{
name|assertNotNull
argument_list|(
name|formatter
operator|.
name|format
argument_list|(
literal|"string"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getDescriptionAlwaysNonEmpty ()
specifier|public
name|void
name|getDescriptionAlwaysNonEmpty
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|formatter
operator|.
name|getDescription
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getExampleInputAlwaysNonEmpty ()
specifier|public
name|void
name|getExampleInputAlwaysNonEmpty
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|formatter
operator|.
name|getExampleInput
argument_list|()
operator|.
name|isEmpty
argument_list|()
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
literal|"{index}: {0}"
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
comment|// all classes implementing {@link net.sf.jabref.logic.formatter.Formatter}
comment|// sorted alphabetically
comment|// Alternative: Use reflection - https://github.com/ronmamo/reflections
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
operator|new
name|CapitalizeFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|ClearFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|HtmlToLatexFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|HtmlToUnicodeFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|IdentityFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|LatexCleanupFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|LatexToUnicodeFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|LowerCaseFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|MinifyNameListFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|NormalizeDateFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|NormalizeMonthFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|NormalizeNamesFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|NormalizePagesFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|OrdinalsToSuperscriptFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|ProtectTermsFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|RemoveBracesFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|SentenceCaseFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|TitleCaseFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|UnicodeToLatexFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|UnitsToLatexFormatter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|UpperCaseFormatter
argument_list|()
block|}
argument_list|)
return|;
comment|// @formatter:on
block|}
block|}
end_class

end_unit

