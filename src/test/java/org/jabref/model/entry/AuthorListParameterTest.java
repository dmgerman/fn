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
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|ParameterizedTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|provider
operator|.
name|Arguments
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|provider
operator|.
name|MethodSource
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|AuthorListParameterTest
class|class
name|AuthorListParameterTest
block|{
DECL|method|data ()
specifier|private
specifier|static
name|Stream
argument_list|<
name|Arguments
argument_list|>
name|data
parameter_list|()
block|{
return|return
name|Stream
operator|.
name|of
argument_list|(
name|Arguments
operator|.
name|of
argument_list|(
literal|"ç, å"
argument_list|,
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
argument_list|,
name|Arguments
operator|.
name|of
argument_list|(
literal|"Doe, John"
argument_list|,
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
argument_list|,
name|Arguments
operator|.
name|of
argument_list|(
literal|"von Berlichingen zu Hornberg, Johann Gottfried"
argument_list|,
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
argument_list|,
comment|//Arguments.of("Robert and Sons, Inc.", new Author(null, null, null, "Robert and Sons, Inc.", null))),
comment|//Arguments.of("al-á¹¢Äliá¸¥, AbdallÄh", new Author("AbdallÄh", "A.", null, "al-á¹¢Äliá¸¥", null))),
name|Arguments
operator|.
name|of
argument_list|(
literal|"de la VallÃ©e Poussin, Jean Charles Gabriel"
argument_list|,
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
argument_list|,
name|Arguments
operator|.
name|of
argument_list|(
literal|"de la VallÃ©e Poussin, J. C. G."
argument_list|,
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
argument_list|,
name|Arguments
operator|.
name|of
argument_list|(
literal|"{K}ent-{B}oswell, E. S."
argument_list|,
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
argument_list|,
name|Arguments
operator|.
name|of
argument_list|(
literal|"Uhlenhaut, N Henriette"
argument_list|,
operator|new
name|Author
argument_list|(
literal|"N Henriette"
argument_list|,
literal|"N. H."
argument_list|,
literal|null
argument_list|,
literal|"Uhlenhaut"
argument_list|,
literal|null
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|ParameterizedTest
annotation|@
name|MethodSource
argument_list|(
literal|"data"
argument_list|)
DECL|method|parseCorrectly (String authorsString, Author authorsParsed)
name|void
name|parseCorrectly
parameter_list|(
name|String
name|authorsString
parameter_list|,
name|Author
name|authorsParsed
parameter_list|)
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
operator|new
name|AuthorList
argument_list|(
name|authorsParsed
argument_list|)
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

