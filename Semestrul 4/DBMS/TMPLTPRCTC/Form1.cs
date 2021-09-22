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

namespace Examen
{
    public partial class Form1 : Form
    {
        // establishes connection with the database
        SqlConnection conn;

        DataSet ds;

        // we need 2 data adapters, 1 for each table (brings/sends changes from/to the database)
        SqlDataAdapter daPosts, daUsers;

        SqlCommandBuilder cbPosts;

        // bind control to Binding source then bind it to the data source (e.g. ds = the ships data table); control - BS - ds
        BindingSource bsPosts, bsUsers;

        public Form1()
        {
            InitializeComponent();
        }

        private void btnSaveChanges_Click(object sender, EventArgs e)
        {
            // updates data in the database with the changes done in VSCode after running 
            daPosts.Update(ds, "Posts");
        }

        // if we write the next function by hand, then we have to go to Form1.Designer.cs and search for   " this.Load += new System.EventHandler(this.Form1_Load);" and manually insert the function name
        private void Form1_Load(object sender, EventArgs e)
        {
            conn = new SqlConnection(@"Data Source = DESKTOP-52KHL8B\SQLEXPRESS; Initial Catalog = MiniFacebook; " + " Integrated Security = SSPI; ");
            ds = new DataSet();
            daUsers = new SqlDataAdapter("SELECT * FROM Users", conn);
            daPosts = new SqlDataAdapter("SELECT * FROM Posts", conn);
            cbPosts = new SqlCommandBuilder(daPosts);
            daUsers.Fill(ds, "Users");
            daPosts.Fill(ds, "Posts");

            // created and added a relation between tables Ships and Pirates; *adds a unique constraint* Parent row = Pid in Players, Children rows = Pid in Injuries
            DataRelation dr = new DataRelation("[FK__Posts__Uid__44FF419A]", ds.Tables["Users"].Columns["Uid"], ds.Tables["Posts"].Columns["Uid"]);
            ds.Relations.Add(dr);

            // binding the binding source to the data source
            bsUsers = new BindingSource();
            bsUsers.DataSource = ds;
            bsUsers.DataMember = "Users";

            // we bind pirates using the relation between pirates and ships, so when we select a ship after running the code, only the pirates with that ShipID appear
            bsPosts = new BindingSource(bsUsers, "[FK__Posts__Uid__44FF419A]");


            // doing this below would show us all Injuries whatever player we've chosen after running

            dgvInjuries.DataSource = bsPosts;
            dgvPlayers.DataSource = bsUsers;

        }

    }
}
