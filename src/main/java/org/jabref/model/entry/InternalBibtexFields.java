begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_comment
comment|/**  * Handling of bibtex fields.  * All bibtex-field related stuff should be placed here!  * Because we can export this information into additional  * config files -> simple extension and definition of new fields  *  * TODO:  * - handling of identically fields with different names (https://github.com/JabRef/jabref/issues/521)  * e.g. LCCN = lib-congress, journaltitle = journal  * - group id for each fields, e.g. standard, jurabib, bio, ...  * - add a additional properties functionality into the BibField class  */
end_comment

begin_class
DECL|class|InternalBibtexFields
specifier|public
class|class
name|InternalBibtexFields
block|{
DECL|method|InternalBibtexFields ()
specifier|private
name|InternalBibtexFields
parameter_list|()
block|{
comment|// Singleton
block|}
block|}
end_class

end_unit

