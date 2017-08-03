begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.architecture
package|package
name|org
operator|.
name|jabref
operator|.
name|architecture
package|;
end_package

begin_comment
comment|/**  * Annotation to indicate that usage of ApacheCommonsLang3 is explicitly allowed.  * The intention is to fully switch to Google Guava and only use Apache Commons Lang3 if there is no other possibility  */
end_comment

begin_annotation_defn
DECL|annotation|ApacheCommonsLang3Allowed
specifier|public
annotation_defn|@interface
name|ApacheCommonsLang3Allowed
block|{
comment|// The rationale
DECL|method|value ()
name|String
name|value
parameter_list|()
function_decl|;
block|}
end_annotation_defn

end_unit

