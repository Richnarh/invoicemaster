package com.khoders.invoicemaster.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expense_item")
public class ExpenseItem extends BillInvoice {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final String _expense = "expense";
    public static final String _expenseId = Expense._id;
    @JoinColumn(name = "expense", referencedColumnName = "id")
    @ManyToOne
    private Expense expense;

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
