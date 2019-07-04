begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.citationstyle
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|citationstyle
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_interface
DECL|interface|PreviewLayout
specifier|public
interface|interface
name|PreviewLayout
block|{
DECL|method|generatePreview (BibEntry entry, BibDatabase database)
name|String
name|generatePreview
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
function_decl|;
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

