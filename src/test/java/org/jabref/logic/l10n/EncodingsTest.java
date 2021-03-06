begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.l10n
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertNotNull
import|;
end_import

begin_class
DECL|class|EncodingsTest
specifier|public
class|class
name|EncodingsTest
block|{
annotation|@
name|Test
DECL|method|charsetsShouldNotBeNull ()
specifier|public
name|void
name|charsetsShouldNotBeNull
parameter_list|()
block|{
name|assertNotNull
argument_list|(
name|Encodings
operator|.
name|ENCODINGS
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|displayNamesShouldNotBeNull ()
specifier|public
name|void
name|displayNamesShouldNotBeNull
parameter_list|()
block|{
name|assertNotNull
argument_list|(
name|Encodings
operator|.
name|ENCODINGS_DISPLAYNAMES
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|charsetsShouldNotBeEmpty ()
specifier|public
name|void
name|charsetsShouldNotBeEmpty
parameter_list|()
block|{
name|assertNotEquals
argument_list|(
literal|0
argument_list|,
name|Encodings
operator|.
name|ENCODINGS
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|displayNamesShouldNotBeEmpty ()
specifier|public
name|void
name|displayNamesShouldNotBeEmpty
parameter_list|()
block|{
name|assertNotEquals
argument_list|(
literal|0
argument_list|,
name|Encodings
operator|.
name|ENCODINGS_DISPLAYNAMES
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

