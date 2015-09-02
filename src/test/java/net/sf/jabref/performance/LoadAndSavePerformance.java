begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.performance
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|performance
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
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

begin_class
DECL|class|LoadAndSavePerformance
specifier|public
class|class
name|LoadAndSavePerformance
block|{
annotation|@
name|Test
argument_list|()
DECL|method|testLoadAndSaveWithGeneratedData ()
specifier|public
name|void
name|testLoadAndSaveWithGeneratedData
parameter_list|()
block|{
comment|// String largeFile = new BibtexEntryGenerator().generateBibtexEntries(99999);
comment|// FIXME this test is incomplete
name|Assert
operator|.
name|assertNull
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

