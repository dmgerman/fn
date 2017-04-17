begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
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
DECL|class|BuildInfoTest
specifier|public
class|class
name|BuildInfoTest
block|{
annotation|@
name|Test
DECL|method|testDefaults ()
specifier|public
name|void
name|testDefaults
parameter_list|()
block|{
name|BuildInfo
name|buildInfo
init|=
operator|new
name|BuildInfo
argument_list|(
literal|"asdf"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"*unknown*"
argument_list|,
name|buildInfo
operator|.
name|getVersion
argument_list|()
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFileImport ()
specifier|public
name|void
name|testFileImport
parameter_list|()
block|{
name|BuildInfo
name|buildInfo
init|=
operator|new
name|BuildInfo
argument_list|(
literal|"/org/jabref/util/build.properties"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"42"
argument_list|,
name|buildInfo
operator|.
name|getVersion
argument_list|()
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|azureInstrumentationKeyIsNotEmpty ()
specifier|public
name|void
name|azureInstrumentationKeyIsNotEmpty
parameter_list|()
block|{
name|BuildInfo
name|buildInfo
init|=
operator|new
name|BuildInfo
argument_list|()
decl_stmt|;
name|assertNotNull
argument_list|(
name|buildInfo
operator|.
name|getAzureInstrumentationKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertNotEquals
argument_list|(
literal|""
argument_list|,
name|buildInfo
operator|.
name|getAzureInstrumentationKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

