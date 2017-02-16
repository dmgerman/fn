begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.openoffice
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|openoffice
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
name|entry
operator|.
name|BibEntry
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
name|FieldName
import|;
end_import

begin_comment
comment|/**  * Subclass of BibEntry for representing entries referenced in a document that can't  * be found in JabRef's current database.  */
end_comment

begin_class
DECL|class|UndefinedBibtexEntry
specifier|public
class|class
name|UndefinedBibtexEntry
extends|extends
name|BibEntry
block|{
DECL|field|key
specifier|private
specifier|final
name|String
name|key
decl_stmt|;
DECL|method|UndefinedBibtexEntry (String key)
specifier|public
name|UndefinedBibtexEntry
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|this
operator|.
name|key
operator|=
name|key
expr_stmt|;
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|OOBibStyle
operator|.
name|UNDEFINED_CITATION_MARKER
argument_list|)
expr_stmt|;
block|}
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|key
return|;
block|}
block|}
end_class

end_unit

