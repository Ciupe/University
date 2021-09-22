using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Configuration;
using System.Collections.Specialized;

namespace Assignment1
{
    public partial class Form1 : Form
    {
        // establishes connection with the database
        SqlConnection conn;

        String firstTable, secondTable, foreignKey, firstTablePK, secondTableFK, firstTableSelect, secondTableSelect;

        DataSet ds;

        // we need 2 data adapters, 1 for each table (brings/sends changes from/to the database)
        SqlDataAdapter daSecondTable, daFirstTable;

        SqlCommandBuilder cbInjuries;

        // bind control to Binding source then bind it to the data source (e.g. ds = the ships data table); control - BS - ds
        BindingSource bsSecondTable, bsFirstTable;

        public Form1()
        {
            InitializeComponent();
        }



        private void btnSaveChanges_Click(object sender, EventArgs e)
        {
            // updates data in the database with the changes done in VSCode after running 
            daSecondTable.Update(ds, secondTable);
        }

        // if we write the next function by hand, then we have to go to Form1.Designer.cs and search for   " this.Load += new System.EventHandler(this.Form1_Load);" and manually insert the function name
        private void Form1_Load(object sender, EventArgs e)
        {
            firstTable = ConfigurationManager.AppSettings.Get("FirstTable");
            secondTable = ConfigurationManager.AppSettings.Get("SecondTable");
            foreignKey = ConfigurationManager.AppSettings.Get("ForeignKey");
            firstTablePK = ConfigurationManager.AppSettings.Get("FirstTablePK");
            secondTableFK = ConfigurationManager.AppSettings.Get("SecondTableFK");
            firstTableSelect = ConfigurationManager.AppSettings.Get("SelectFirstTable");
            secondTableSelect = ConfigurationManager.AppSettings.Get("SelectSecondTable");

            conn = new SqlConnection(@"Data Source = DESKTOP-52KHL8B\SQLEXPRESS; Initial Catalog = SportsClubs; " + " Integrated Security = SSPI; ");
            ds = new DataSet();
            daFirstTable = new SqlDataAdapter(firstTableSelect, conn);
            daSecondTable = new SqlDataAdapter(secondTableSelect, conn);
            cbInjuries = new SqlCommandBuilder(daSecondTable);
            daFirstTable.Fill(ds, firstTable);
            daSecondTable.Fill(ds, secondTable);

            // created and added a relation between tables Ships and Pirates; *adds a unique constraint* Parent row = Pid in Players, Children rows = Pid in Injuries
            DataRelation dr = new DataRelation(foreignKey, ds.Tables[firstTable].Columns[firstTablePK], ds.Tables[secondTable].Columns[secondTableFK]);
            ds.Relations.Add(dr);

            // binding the binding source to the data source
            bsFirstTable = new BindingSource();
            bsFirstTable.DataSource = ds;
            bsFirstTable.DataMember = firstTable;

            // we bind pirates using the relation between pirates and ships, so when we select a ship after running the code, only the pirates with that ShipID appear
            bsSecondTable = new BindingSource(bsFirstTable, foreignKey);


            // doing this below would show us all Injuries whatever player we've chosen after running

            dgvInjuries.DataSource = bsSecondTable;
            dgvPlayers.DataSource = bsFirstTable;

        }

    }
}
