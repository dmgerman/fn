begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

begin_enum
DECL|enum|TrustLevel
specifier|public
enum|enum
name|TrustLevel
block|{
DECL|enumConstant|SOURCE
name|SOURCE
argument_list|(
literal|3
argument_list|)
block|,
DECL|enumConstant|PUBLISHER
name|PUBLISHER
argument_list|(
literal|2
argument_list|)
block|,
DECL|enumConstant|PREPRINT
name|PREPRINT
argument_list|(
literal|1
argument_list|)
block|,
DECL|enumConstant|META_SEARCH
name|META_SEARCH
argument_list|(
literal|1
argument_list|)
block|,
DECL|enumConstant|UNKNOWN
name|UNKNOWN
argument_list|(
literal|0
argument_list|)
block|;
DECL|field|score
specifier|private
name|int
name|score
decl_stmt|;
DECL|method|TrustLevel (int score)
name|TrustLevel
parameter_list|(
name|int
name|score
parameter_list|)
block|{
name|this
operator|.
name|score
operator|=
name|score
expr_stmt|;
block|}
DECL|method|getTrustScore ()
specifier|public
name|int
name|getTrustScore
parameter_list|()
block|{
return|return
name|this
operator|.
name|score
return|;
block|}
block|}
end_enum

end_unit

