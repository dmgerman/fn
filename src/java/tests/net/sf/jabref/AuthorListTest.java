begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
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
name|AuthorList
import|;
end_import

begin_comment
comment|/**  *   * @author Christopher Oezbek<oezi@oezi.de>  * @version 0.1 - Still fails for stuff in AuthorList that is ambiguous  */
end_comment

begin_class
DECL|class|AuthorListTest
specifier|public
class|class
name|AuthorListTest
extends|extends
name|TestCase
block|{
DECL|method|testAuthorList ()
specifier|public
name|void
name|testAuthorList
parameter_list|()
block|{
name|fail
argument_list|(
literal|"Is that really wanted that there is no way to access any of the fields of the Author?"
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"And should we not rather use getAuthorList anyway"
argument_list|)
expr_stmt|;
block|}
DECL|method|testFixAuthor_Natbib ()
specifier|public
name|void
name|testFixAuthor_Natbib
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_Natbib
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_Natbib
argument_list|(
literal|"John Smith"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith and Black Brown"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_Natbib
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann et al."
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_Natbib
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Is not cached!
name|assertTrue
argument_list|(
name|AuthorList
operator|.
name|fixAuthor_Natbib
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
operator|==
name|AuthorList
operator|.
name|fixAuthor_Natbib
argument_list|(
literal|"John von Neumann"
operator|+
operator|(
literal|0
operator|==
literal|1
condition|?
literal|""
else|:
literal|" and "
operator|)
operator|+
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetAuthorList ()
specifier|public
name|void
name|testGetAuthorList
parameter_list|()
block|{
comment|// Test caching in authorCache.
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
literal|"John Smith"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|al
operator|==
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
literal|"John Smith"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|al
operator|==
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
literal|"Smith"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testFixAuthor_firstNameFirstCommas ()
specifier|public
name|void
name|testFixAuthor_firstNameFirstCommas
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|""
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|""
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Smith"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John Smith"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J. Smith"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John Smith"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check caching
name|assertTrue
argument_list|(
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|,
literal|true
argument_list|)
operator|==
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John von Neumann"
operator|+
operator|(
literal|0
operator|==
literal|1
condition|?
literal|""
else|:
literal|" and "
operator|)
operator|+
literal|"John Smith and Black Brown, Peter"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Smith and Peter Black Brown"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J. Smith and P. Black Brown"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
comment|// Method description is different than code -> additional comma there
name|assertEquals
argument_list|(
literal|"John von Neumann, John Smith and Peter Black Brown"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J. von Neumann, J. Smith and P. Black Brown"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J. P. von Neumann"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
literal|"John Peter von Neumann"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testFixAuthor_firstNameFirst ()
specifier|public
name|void
name|testFixAuthor_firstNameFirst
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"John Smith"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirst
argument_list|(
literal|"John Smith"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Smith and Peter Black Brown"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirst
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John von Neumann and John Smith and Peter Black Brown"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirst
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"First von Last, Jr. III"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_firstNameFirst
argument_list|(
literal|"von Last, Jr. III, First"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check caching
name|assertTrue
argument_list|(
name|AuthorList
operator|.
name|fixAuthor_firstNameFirst
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
operator|==
name|AuthorList
operator|.
name|fixAuthor_firstNameFirst
argument_list|(
literal|"John von Neumann"
operator|+
operator|(
literal|0
operator|==
literal|1
condition|?
literal|""
else|:
literal|" and "
operator|)
operator|+
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testFixAuthor_lastNameFirstCommas ()
specifier|public
name|void
name|testFixAuthor_lastNameFirstCommas
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|""
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|""
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, John"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John Smith"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, J."
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John Smith"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check caching
name|assertTrue
argument_list|(
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|,
literal|true
argument_list|)
operator|==
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John von Neumann"
operator|+
operator|(
literal|0
operator|==
literal|1
condition|?
literal|""
else|:
literal|" and "
operator|)
operator|+
literal|"John Smith and Black Brown, Peter"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, John and Black Brown, Peter"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, J. and Black Brown, P."
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
comment|// Method description is different than code -> additional comma there
name|assertEquals
argument_list|(
literal|"von Neumann, John, Smith, John and Black Brown, Peter"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann, J., Smith, J. and Black Brown, P."
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann, J. P."
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
literal|"John Peter von Neumann"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testFixAuthor_lastNameFirst ()
specifier|public
name|void
name|testFixAuthor_lastNameFirst
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, John"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
literal|"John Smith"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, John and Black Brown, Peter"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Last, Jr, First"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
literal|"von Last, Jr ,First"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
operator|==
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
literal|"John von Neumann"
operator|+
operator|(
literal|0
operator|==
literal|1
condition|?
literal|""
else|:
literal|" and "
operator|)
operator|+
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testFixAuthor_lastNameOnlyCommas ()
specifier|public
name|void
name|testFixAuthor_lastNameOnlyCommas
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
literal|"John Smith"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
literal|"Smith, Jr, John"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
operator|==
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
literal|"John von Neumann"
operator|+
operator|(
literal|0
operator|==
literal|1
condition|?
literal|""
else|:
literal|" and "
operator|)
operator|+
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Method description is different than code
name|assertEquals
argument_list|(
literal|"von Neumann, Smith and Black Brown"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testFixAuthorForAlphabetization ()
specifier|public
name|void
name|testFixAuthorForAlphabetization
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, J."
argument_list|,
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
literal|"John Smith"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Neumann, J."
argument_list|,
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
literal|"John von Neumann"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Neumann, J."
argument_list|,
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
literal|"J. von Neumann"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Neumann, J. and Smith, J. and Black Brown, Jr., P."
argument_list|,
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
literal|"John von Neumann and John Smith and de Black Brown, Jr., Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|size (String bibtex)
specifier|public
specifier|static
name|int
name|size
parameter_list|(
name|String
name|bibtex
parameter_list|)
block|{
return|return
operator|(
operator|new
name|AuthorList
argument_list|(
name|bibtex
argument_list|)
operator|)
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|testSize ()
specifier|public
name|void
name|testSize
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|size
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|size
argument_list|(
literal|"Bar"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|size
argument_list|(
literal|"Foo Bar"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|size
argument_list|(
literal|"Foo von Bar"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|size
argument_list|(
literal|"von Bar, Foo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|size
argument_list|(
literal|"Bar, Foo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|size
argument_list|(
literal|"Bar, Jr., Foo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|size
argument_list|(
literal|"Bar, Foo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|size
argument_list|(
literal|"John Neumann and Foo Bar"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|size
argument_list|(
literal|"John von Neumann and Bar, Jr, Foo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|size
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|s
init|=
literal|"John von Neumann"
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
literal|25
condition|;
name|i
operator|++
control|)
block|{
name|assertEquals
argument_list|(
name|i
operator|+
literal|1
argument_list|,
name|size
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
name|s
operator|+=
literal|" and Albert Einstein"
expr_stmt|;
block|}
block|}
DECL|method|testGetAuthor ()
specifier|public
name|void
name|testGetAuthor
parameter_list|()
block|{
name|fail
argument_list|(
literal|"getAuthor() is public, but Author is private. Thus cannot be used."
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetAuthorsNatbib ()
specifier|public
name|void
name|testGetAuthorsNatbib
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|""
argument_list|)
operator|.
name|getAuthorsNatbib
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John Smith"
argument_list|)
operator|.
name|getAuthorsNatbib
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith and Black Brown"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
operator|.
name|getAuthorsNatbib
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann et al."
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
operator|.
name|getAuthorsNatbib
argument_list|()
argument_list|)
expr_stmt|;
comment|// Test caching
name|AuthorList
name|al
init|=
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|al
operator|.
name|getAuthorsNatbib
argument_list|()
operator|==
name|al
operator|.
name|getAuthorsNatbib
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetAuthorsLastOnly ()
specifier|public
name|void
name|testGetAuthorsLastOnly
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|""
argument_list|)
operator|.
name|getAuthorsLastOnly
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John Smith"
argument_list|)
operator|.
name|getAuthorsLastOnly
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"Smith, Jr, John"
argument_list|)
operator|.
name|getAuthorsLastOnly
argument_list|()
argument_list|)
expr_stmt|;
comment|// Method description is different than code
name|assertEquals
argument_list|(
literal|"von Neumann, Smith and Black Brown"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
operator|.
name|getAuthorsLastOnly
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetAuthorsLastFirst ()
specifier|public
name|void
name|testGetAuthorsLastFirst
parameter_list|()
block|{
name|AuthorList
name|al
decl_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|"John Smith"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, John"
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, J."
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, John and Black Brown, Peter"
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, J. and Black Brown, P."
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
expr_stmt|;
comment|// Method description is different than code -> additional comma there
name|assertEquals
argument_list|(
literal|"von Neumann, John, Smith, John and Black Brown, Peter"
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann, J., Smith, J. and Black Brown, P."
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|"John Peter von Neumann"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann, J. P."
argument_list|,
name|al
operator|.
name|getAuthorsLastFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetAuthorsLastFirstAnds ()
specifier|public
name|void
name|testGetAuthorsLastFirstAnds
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, John"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John Smith"
argument_list|)
operator|.
name|getAuthorsLastFirstAnds
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Smith, John and Black Brown, Peter"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
operator|.
name|getAuthorsLastFirstAnds
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
operator|.
name|getAuthorsLastFirstAnds
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Last, Jr, First"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"von Last, Jr ,First"
argument_list|)
operator|.
name|getAuthorsLastFirstAnds
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetAuthorsFirstFirst ()
specifier|public
name|void
name|testGetAuthorsFirstFirst
parameter_list|()
block|{
name|AuthorList
name|al
decl_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|"John Smith"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Smith"
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J. Smith"
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Smith and Peter Black Brown"
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J. Smith and P. Black Brown"
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
expr_stmt|;
comment|// Method description is different than code -> additional comma there
name|assertEquals
argument_list|(
literal|"John von Neumann, John Smith and Peter Black Brown"
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J. von Neumann, J. Smith and P. Black Brown"
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|=
operator|new
name|AuthorList
argument_list|(
literal|"John Peter von Neumann"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J. P. von Neumann"
argument_list|,
name|al
operator|.
name|getAuthorsFirstFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetAuthorsFirstFirstAnds ()
specifier|public
name|void
name|testGetAuthorsFirstFirstAnds
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"John Smith"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John Smith"
argument_list|)
operator|.
name|getAuthorsFirstFirstAnds
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Smith and Peter Black Brown"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
operator|.
name|getAuthorsFirstFirstAnds
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John von Neumann and John Smith and Peter Black Brown"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
operator|.
name|getAuthorsFirstFirstAnds
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"First von Last, Jr. III"
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"von Last, Jr. III, First"
argument_list|)
operator|.
name|getAuthorsFirstFirstAnds
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetAuthorsForAlphabetization ()
specifier|public
name|void
name|testGetAuthorsForAlphabetization
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, J."
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John Smith"
argument_list|)
operator|.
name|getAuthorsForAlphabetization
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Neumann, J."
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann"
argument_list|)
operator|.
name|getAuthorsForAlphabetization
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Neumann, J."
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"J. von Neumann"
argument_list|)
operator|.
name|getAuthorsForAlphabetization
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Neumann, J. and Smith, J. and Black Brown, Jr., P."
argument_list|,
operator|new
name|AuthorList
argument_list|(
literal|"John von Neumann and John Smith and de Black Brown, Jr., Peter"
argument_list|)
operator|.
name|getAuthorsForAlphabetization
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

