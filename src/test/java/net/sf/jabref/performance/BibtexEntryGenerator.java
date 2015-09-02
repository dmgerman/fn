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

begin_class
DECL|class|BibtexEntryGenerator
specifier|public
class|class
name|BibtexEntryGenerator
block|{
DECL|method|generateBibtexEntries (int number)
specifier|public
name|String
name|generateBibtexEntries
parameter_list|(
name|int
name|number
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|number
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|generateBibtexEntry
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|generateBibtexEntry (int i)
specifier|private
name|String
name|generateBibtexEntry
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
literal|"@article{einstein1916grundlage"
operator|+
name|i
operator|+
literal|",\n"
operator|+
literal|"  title={Die grundlage der allgemeinen relativit{\\\"a}tstheorie},\n"
operator|+
literal|"  author={Einstein, Albert},\n"
operator|+
literal|"  journal={Annalen der Physik},\n"
operator|+
literal|"  volume={354},\n"
operator|+
literal|"  number={7},\n"
operator|+
literal|"  pages={769--822},\n"
operator|+
literal|"  year={1916},\n"
operator|+
literal|"  publisher={Wiley Online Library}\n"
operator|+
literal|"}\n"
return|;
block|}
block|}
end_class

end_unit

