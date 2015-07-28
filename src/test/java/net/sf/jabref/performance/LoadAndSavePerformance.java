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
DECL|method|testLoadAndSaveWithGeneratedData ()
specifier|public
name|void
name|testLoadAndSaveWithGeneratedData
parameter_list|()
block|{
name|String
name|largeFile
init|=
operator|new
name|BibtexEntryGenerator
argument_list|()
operator|.
name|generateBibtexEntries
argument_list|(
literal|99999
argument_list|)
decl_stmt|;
block|}
block|}
end_class

end_unit

