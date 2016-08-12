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

begin_class
DECL|class|SpecialFields
specifier|public
class|class
name|SpecialFields
block|{
DECL|field|FIELDNAME_PRINTED
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_PRINTED
init|=
literal|"printed"
decl_stmt|;
DECL|field|FIELDNAME_PRIORITY
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_PRIORITY
init|=
literal|"priority"
decl_stmt|;
DECL|field|FIELDNAME_QUALITY
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_QUALITY
init|=
literal|"qualityassured"
decl_stmt|;
DECL|field|FIELDNAME_RANKING
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_RANKING
init|=
literal|"ranking"
decl_stmt|;
DECL|field|FIELDNAME_READ
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_READ
init|=
literal|"readstatus"
decl_stmt|;
DECL|field|FIELDNAME_RELEVANCE
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_RELEVANCE
init|=
literal|"relevance"
decl_stmt|;
DECL|field|PREF_SPECIALFIELDSENABLED_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SPECIALFIELDSENABLED_DEFAULT
init|=
name|Boolean
operator|.
name|TRUE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_PRINTED_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_PRINTED_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_PRIORITY_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_PRIORITY_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_QUALITY_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_QUALITY_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_RANKING_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_RANKING_DEFAULT
init|=
name|Boolean
operator|.
name|TRUE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_READ_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_READ_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_RELEVANCE_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_RELEVANCE_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS_DEFAULT
init|=
name|Boolean
operator|.
name|TRUE
decl_stmt|;
DECL|field|PREF_SERIALIZESPECIALFIELDS_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SERIALIZESPECIALFIELDS_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
block|}
end_class

end_unit

