begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|BibEntryEqualityTest
specifier|public
class|class
name|BibEntryEqualityTest
block|{
annotation|@
name|Test
DECL|method|identicObjectsareEqual ()
specifier|public
name|void
name|identicObjectsareEqual
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|BibEntry
name|e2
init|=
name|e1
decl_stmt|;
name|assertTrue
argument_list|(
name|e1
operator|.
name|equals
argument_list|(
name|e2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareToNullObjectIsFalse ()
specifier|public
name|void
name|compareToNullObjectIsFalse
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|assertFalse
argument_list|(
name|e1
operator|.
name|equals
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareToDifferentClassIsFalse ()
specifier|public
name|void
name|compareToDifferentClassIsFalse
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|Object
name|e2
init|=
operator|new
name|Object
argument_list|()
decl_stmt|;
name|assertFalse
argument_list|(
name|e1
operator|.
name|equals
argument_list|(
name|e2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareIsTrueWhenIdAndFieldsAreEqual ()
specifier|public
name|void
name|compareIsTrueWhenIdAndFieldsAreEqual
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|e1
operator|.
name|setId
argument_list|(
literal|"1"
argument_list|)
expr_stmt|;
name|e1
operator|.
name|setField
argument_list|(
literal|"key"
argument_list|,
literal|"value"
argument_list|)
expr_stmt|;
name|BibEntry
name|e2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|e2
operator|.
name|setId
argument_list|(
literal|"1"
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|e1
operator|.
name|equals
argument_list|(
name|e2
argument_list|)
argument_list|)
expr_stmt|;
name|e2
operator|.
name|setField
argument_list|(
literal|"key"
argument_list|,
literal|"value"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|e1
operator|.
name|equals
argument_list|(
name|e2
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
