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

namespace Lab1
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=DESKTOP-Q7R4LPJ\\SQLEXPRESS;Initial Catalog=OuterSpace;Integrated Security=True");
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();

        public Form1()
        {
            InitializeComponent();
            ds.Tables.Add("Galaxies");
            ds.Tables.Add("Constellations");
        }

        //populate parent
        private void button1_Click(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("Select * from Galaxies", cs);
            ds.Clear();
            da.Fill(ds.Tables["Galaxies"]);
            dataGridView1.DataSource = ds.Tables["Galaxies"];
        }

        //popualte child
        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                int rowIndex = (int)dataGridView1.SelectedCells[0].RowIndex;
                int Gid = (int)dataGridView1.Rows[rowIndex].Cells[0].Value;
                da.SelectCommand = new SqlCommand("Select * from Constellations where Gid = @Gid", cs);
                da.SelectCommand.Parameters.Add("@Gid", SqlDbType.Int).Value = Gid;
                ds.Tables["Constellations"].Clear();
                da.Fill(ds.Tables["Constellations"]);
                dataGridView2.DataSource = ds.Tables["Constellations"];
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }

        //add button
        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                int rowIndex = (int)dataGridView1.SelectedCells[0].RowIndex;
                int Gid = (int)dataGridView1.Rows[rowIndex].Cells[0].Value;
                da.InsertCommand = new SqlCommand("Insert into Constellations values (@nrStars, @gid)", cs);
                da.InsertCommand.Parameters.Add("@nrStars", SqlDbType.Int).Value = Int32.Parse(textBox1.Text);
                da.InsertCommand.Parameters.Add("@gid", SqlDbType.Int).Value = Gid;
                cs.Open();
                da.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Successfully inserted into Constellations!");
                cs.Close();
                ds.Tables["Constellations"].Clear();
                da.Fill(ds.Tables["Constellations"]);
                dataGridView2.DataSource = ds.Tables["Constellations"];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }
        //remove button
        private void button4_Click(object sender, EventArgs e)
        {
            try
            {
                int rowIndex = (int)dataGridView1.SelectedCells[0].RowIndex;
                int Gid = (int)dataGridView1.Rows[rowIndex].Cells[0].Value;
                da.DeleteCommand = new SqlCommand("delete from Constellations where Gid=@gid", cs);
                da.DeleteCommand.Parameters.Add("@gid", SqlDbType.Int).Value = Gid;
                cs.Open();
                da.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Successfully deleted from Constellations!");
                cs.Close();
                ds.Tables["Constellations"].Clear();
                da.Fill(ds.Tables["Constellations"]);
                dataGridView2.DataSource = ds.Tables["Constellations"];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }
        //update button
        private void button5_Click(object sender, EventArgs e)
        {
            try
            {
                int rowIndex = (int)dataGridView1.SelectedCells[0].RowIndex;
                int Gid = (int)dataGridView1.Rows[rowIndex].Cells[0].Value;
                da.UpdateCommand = new SqlCommand("update Constellations set NrStars=@nrStars where Gid=@gid", cs);
                da.UpdateCommand.Parameters.Add("@nrStars", SqlDbType.Int).Value = Int32.Parse(textBox1.Text);
                da.UpdateCommand.Parameters.Add("@gid", SqlDbType.Int).Value = Gid;
                cs.Open();
                da.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Successfully updated into Constellations!");
                cs.Close();
                ds.Tables["Constellations"].Clear();
                da.Fill(ds.Tables["Constellations"]);
                dataGridView2.DataSource = ds.Tables["Constellations"];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
