begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Ignore
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
name|assertEquals
import|;
end_import

begin_class
DECL|class|AuthorListTest
specifier|public
class|class
name|AuthorListTest
block|{
annotation|@
name|Ignore
annotation|@
name|Test
DECL|method|authorListTest ()
specifier|public
name|void
name|authorListTest
parameter_list|()
block|{
name|String
name|authorString
init|=
literal|"Olaf von Nilsen, Jr."
decl_stmt|;
name|AuthorList
name|authorList
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|authorString
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|authorList
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|AuthorList
operator|.
name|Author
name|author
init|=
name|authorList
operator|.
name|getAuthor
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Olaf"
argument_list|,
name|author
operator|.
name|getFirst
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Nilsen"
argument_list|,
name|author
operator|.
name|getLast
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Jr."
argument_list|,
name|author
operator|.
name|getJr
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von"
argument_list|,
name|author
operator|.
name|getVon
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

