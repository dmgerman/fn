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

begin_class
DECL|class|BibtexEntryTests
specifier|public
class|class
name|BibtexEntryTests
block|{
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
annotation|@
name|Test
DECL|method|testDefaultConstructor ()
specifier|public
name|void
name|testDefaultConstructor
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibtexEntry
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

