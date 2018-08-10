begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
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
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameter
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
operator|.
name|Parameters
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

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|AuthorListParameterTest
specifier|public
class|class
name|AuthorListParameterTest
block|{
annotation|@
name|Parameter
argument_list|(
name|value
operator|=
literal|0
argument_list|)
DECL|field|authorsString
specifier|public
name|String
name|authorsString
decl_stmt|;
annotation|@
name|Parameter
argument_list|(
name|value
operator|=
literal|1
argument_list|)
DECL|field|authorsParsed
specifier|public
name|AuthorList
name|authorsParsed
decl_stmt|;
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"{index}: parse({0})={1}"
argument_list|)
DECL|method|data ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|Object
index|[]
argument_list|>
name|data
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
index|[]
block|{
block|{
literal|"ç, å"
block|,
name|authorList
argument_list|(
operator|new
name|Author
argument_list|(
literal|"å"
argument_list|,
literal|"å."
argument_list|,
literal|null
argument_list|,
literal|"ç"
argument_list|,
literal|null
argument_list|)
argument_list|)
block|}
block|,
block|{
literal|"Doe, John"
block|,
name|authorList
argument_list|(
operator|new
name|Author
argument_list|(
literal|"John"
argument_list|,
literal|"J."
argument_list|,
literal|null
argument_list|,
literal|"Doe"
argument_list|,
literal|null
argument_list|)
argument_list|)
block|}
block|,
block|{
literal|"von Berlichingen zu Hornberg, Johann Gottfried"
block|,
name|authorList
argument_list|(
operator|new
name|Author
argument_list|(
literal|"Johann Gottfried"
argument_list|,
literal|"J. G."
argument_list|,
literal|"von"
argument_list|,
literal|"Berlichingen zu Hornberg"
argument_list|,
literal|null
argument_list|)
argument_list|)
block|}
block|,
comment|//{ "Robert and Sons, Inc.", authorList(new Author(null, null, null, "Robert and Sons, Inc.", null)) },
comment|//{ "al-á¹¢Äliá¸¥, AbdallÄh", authorList(new Author("AbdallÄh", "A.", null, "al-á¹¢Äliá¸¥", null)) },
block|{
literal|"de la VallÃ©e Poussin, Jean Charles Gabriel"
block|,
name|authorList
argument_list|(
operator|new
name|Author
argument_list|(
literal|"Jean Charles Gabriel"
argument_list|,
literal|"J. C. G."
argument_list|,
literal|"de la"
argument_list|,
literal|"VallÃ©e Poussin"
argument_list|,
literal|null
argument_list|)
argument_list|)
block|}
block|,
block|{
literal|"de la VallÃ©e Poussin, J. C. G."
block|,
name|authorList
argument_list|(
operator|new
name|Author
argument_list|(
literal|"J. C. G."
argument_list|,
literal|"J. C. G."
argument_list|,
literal|"de la"
argument_list|,
literal|"VallÃ©e Poussin"
argument_list|,
literal|null
argument_list|)
argument_list|)
block|}
block|,
block|{
literal|"{K}ent-{B}oswell, E. S."
block|,
name|authorList
argument_list|(
operator|new
name|Author
argument_list|(
literal|"E. S."
argument_list|,
literal|"E. S."
argument_list|,
literal|null
argument_list|,
literal|"{K}ent-{B}oswell"
argument_list|,
literal|null
argument_list|)
argument_list|)
block|}
block|,         }
argument_list|)
return|;
block|}
DECL|method|authorList (Author author)
specifier|public
specifier|static
name|AuthorList
name|authorList
parameter_list|(
name|Author
name|author
parameter_list|)
block|{
return|return
operator|new
name|AuthorList
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|author
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectly ()
specifier|public
name|void
name|parseCorrectly
parameter_list|()
block|{
name|AuthorListParser
name|parser
init|=
operator|new
name|AuthorListParser
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|authorsParsed
argument_list|,
name|parser
operator|.
name|parse
argument_list|(
name|authorsString
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

