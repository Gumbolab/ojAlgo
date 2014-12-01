package org.ojalgo.optimisation.convex;

import java.math.BigDecimal;

import org.ojalgo.constant.BigMath;
import org.ojalgo.matrix.BigMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;
import org.ojalgo.optimisation.convex.ConvexSolver;
import org.ojalgo.optimisation.convex.ConvexSolver.Builder;
import org.ojalgo.type.TypeUtils;

class QuadraticDemo {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        try {
            QuadraticDemo.solve2();
        } catch (final Throwable t) {
            t.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    static ExpressionsBasedModel createQuadraticModel(final int size, final MatrixStore<Double> Q, final MatrixStore<Double> C, final MatrixStore<Double> AE,
            final MatrixStore<Double> BE, final MatrixStore<Double> AI, final MatrixStore<Double> BI) {
        //
        // create the model variables with contribution weight and lower / upper limits
        //
        final Variable[] vars = new Variable[size];
        for (int i = 0; i < size; ++i) {
            vars[i] = new Variable("x" + i);
            vars[i].lower(BigMath.ZERO).upper(BigMath.ONE);
        }
        //
        // create an empty quadratic model
        //
        final ExpressionsBasedModel model = new ExpressionsBasedModel(vars);
        //
        // add the quadratic objective function
        //
        final Expression objective = model.addExpression("objective");
        objective.weight(BigMath.ONE);
        //
        // set the quadratic factors
        //
        if (Q != null) {
            for (int i = 0; i < Q.countRows(); ++i) {
                for (int j = 0; j < Q.countColumns(); ++j) {
                    final BigDecimal value = TypeUtils.toBigDecimal(Q.get(i, j));
                    objective.setQuadraticFactor(i, j, value);
                }
            }
        }
        //
        // set the linear factors
        //
        if (C != null) {
            final BigDecimal linearWeight = BigMath.TWO.negate();
            for (int i = 0; i < C.countRows(); ++i) {
                final BigDecimal value = TypeUtils.toBigDecimal(C.get(i, 0));
                objective.setLinearFactor(i, value.multiply(linearWeight));
            }
        }
        //    System.out.println(objective);
        //
        // add the equality constraints
        //
        if (AE != null) {
            for (int i = 0; i < AE.countRows(); ++i) {
                final Expression e = model.addExpression("eq" + i);
                for (int j = 0; j < AE.countColumns(); ++j) {
                    final BigDecimal value = TypeUtils.toBigDecimal(AE.get(i, j));
                    e.setLinearFactor(j, value);
                }
                if (BE != null) {
                    final BigDecimal value = TypeUtils.toBigDecimal(BE.get(i, 0));
                    e.level(value);
                }
                //      System.out.println(e);
            }
        }
        //
        // add the inequality constraints
        //
        if (AI != null) {
            for (int i = 0; i < AI.countRows(); ++i) {
                final Expression e = model.addExpression("ie" + i);
                for (int j = 0; j < AI.countColumns(); ++j) {
                    final BigDecimal value = TypeUtils.toBigDecimal(AI.get(i, j));
                    e.setLinearFactor(j, value);
                }
                if (BI != null) {
                    final BigDecimal value = TypeUtils.toBigDecimal(BI.get(i, 0));
                    e.upper(value);
                }
                //   System.out.println(e);
            }
        }
        //
        // done
        //
        return model;
    }

    static ConvexSolver createConvexSolver(final ExpressionsBasedModel model) {
        //		OptimisationSolver solver = model.getDefaultSolver();
        final ConvexSolver.Builder builder = new ConvexSolver.Builder(model);
        final ConvexSolver solver = builder.build();
        return solver;
    }

    static ConvexSolver createConvexSolver(final MatrixStore<Double> Q, final MatrixStore<Double> C, final MatrixStore<Double> AE,
            final MatrixStore<Double> BE, final MatrixStore<Double> AI, final MatrixStore<Double> BI) {
        //		OptimisationSolver solver = model.getDefaultSolver();
        final ConvexSolver.Builder builder = new ConvexSolver.Builder(Q, C).equalities(AE, BE).inequalities(AI, BI);

        final ConvexSolver solver = builder.build();

        return solver;
    }

    static void printMatrix(final String name, final double[][] matrix) {
        if (matrix != null) {
            System.out.println(name + "[" + matrix.length + ", " + matrix[0].length + "]");
            final StringBuilder sb = new StringBuilder();
            final int rows = matrix.length;
            final int cols = matrix[0].length;
            for (int i = 0; i < rows; ++i) {
                sb.append(i).append(": ");
                for (int j = 0; j < cols; ++j) {
                    sb.append(matrix[i][j]).append(j < (cols - 1) ? ", " : "");
                }
                sb.append("\n");
            }
            System.out.println(sb);
        }
    }

    static void solve2() {

        final StringBuilder qs = new StringBuilder(
        final StringBuilder aes = new StringBuilder(
                "1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0");
        final StringBuilder ais = new StringBuilder(
                "3.324526516483995, -0.6845939255914701, 2.121996570438758, -0.5149542609131021, 1.685106939620263, -0.6827849649406216, 1.7601477434437818, -0.6467217676643896, 1.194687599530417, -0.6494940538591925, 0.4915664099452252, -0.4400304704784979, -0.1354229473073587, -2.312566563656755, 3.229277334641411, -0.8616678556369259, 1.2821071375394089, -0.6533001649953997, 1.5263965632687997, -0.2660352814343349, 1.5540168476803216, -1.1566711079728271, 3.205477476196286, -0.56858565554503, -0.28254246967742175, -0.2259897136334874, 2.991657968400343, -0.2557570900830821, -0.7514831825663533, -0.5332735933894124, 1.1810862732304637, -1.4920919345184893, 1.8914363095437545, -0.7076990438572032, 2.687977547701872, -1.2555715692025289, 3.458836330019933, -1.0365809444559915, 0.3570678087906969, -0.48210662335914306, 1.8516681157690213, -0.9661089408122651, 3.4058863434763653, -0.8554466269261578, 2.701847757233905, -0.4812805863257143, 1.7649370967555515, -0.7556751023551157, 1.4924380547934784, -0.31232335351100765, 1.3404967211789058, -0.2726221824664384, 1.054026394354366, -0.43872733942451386, 2.885296609290311, -0.4393962584045205, 3.098538104381003, -0.40350727655407825, 3.2636362899305973, -0.4186728115475047, 4.035616623756567, -0.4485692810706361, 4.294596464668736, -0.5924486632910088, 2.2972075221229944, -1.241064296762711, 1.0301063231092031, -0.30477862460629257, 3.5080878438079117, -0.6965971354560678, 0.39709733364510436, -0.26665030685767377, 3.5473468120423597, -0.44481213670077885, 1.599507054010445, -0.44827970968457675, 0.7788265078054275, -0.350094704020305, 1.6810767369467023, -0.5133154020480338, 2.6313769907711597, -0.33040701949803675, 2.3630968926966847, -1.2837629832317936, 2.9109674798013168, -2.2270722991388046, 3.0409075625162134, -0.4127132037103711, 4.716766993354639, -0.7035465637243867, 1.7186877383970396, -0.5131468129701975, 0.6136175850037189, -0.5380104266919002, 1.0524775572059875, -0.35120376539513715, 0.44836679312603495, -0.09829430265270392, -0.7144730391541715, -0.7550988204911477, -0.4137833630296787, -0.21428832719057403, -0.3994133304669672, -0.7284276056831644, -0.3749535058463946, -0.522774665364021, -0.30314183878590795, -0.5998116750729314, -0.21469237562255894, -0.19001833630119194, -0.007582946832267242, -0.390866246486396, 0.055027737950490443, -0.3447538821454598, 0.07603710436485392, -0.48494490481709396, 0.09174797768953478, -0.27177458728927095, 0.12237803423639394, -0.5661776819998041, 0.14236710261300023, -0.5884325959612936, 0.190006992444598, -0.7585066531348567, 0.21572759089045498, -0.2766657348399288, 0.31956741445663533, -0.7984542254660644, 0.3211962335273641, -1.2667367237477347, 0.3477179060715225, -0.7847938409562342, 0.36051627047645135, -0.9964923351202687, 0.36532663242966207, -0.8712091440003922, 0.39100717717967853, -1.500690271068921, 0.4137865896996201, -0.6185324795234726, 0.4246967062303352, -0.3622860128001902, 0.4332873464441959, -0.60889843318735, 0.48150698575697815, -1.4827555548485225, 0.4870764720832091, -0.3024025925429782, 0.49513694577923123, -1.0325245031397523, 0.49757638221495265, -0.753250289048276, 0.5514571528645293, -0.87844898181445, 0.6060778527709675, -1.0280978963084977, 0.6350371664409509, -0.8304544418667594, 0.6353175256541254, -1.5470057980850092, 0.6557681484748937, -0.4720744423419858, 0.6595770800451474, -0.31441497940108964, 0.6688277750360226, -0.46687300794695175, 0.6913574065408894, -0.8397010888796419, 0.7007777900896653, -0.21673075279489548, 0.7064368038217267, -0.576684628206304, 0.7379271158017501, -0.5777374526490042, 0.7499663332312332, -1.8661939377341958, 0.7703368651733278, -1.3428225868223234, 0.7879664913241455, -0.4481129923583789, 0.8051172965998786, -0.6832328024409549, 0.824497928147664, -0.5035449685649361, 0.8288581084766636, -0.7547397695097516, 0.9075362675120537, -0.7736644810169427, 0.915146627254542, -0.6510374167267237, 0.932537676353489, -0.9157257178887177, 0.9424464515515126, -0.3075636098692458, 0.9807765018089423, -0.3964164059130758, 1.0048567257949395, -0.6791402002265904, 1.031567306017137, -0.7969842205141486, 1.0539271963583914, -0.33396897498623096, 1.0565078735023048, -1.0811044741245484, 1.1442363102423547, -0.35785857727141607, 1.148346645097387, -0.5345964223382909, 1.1795070236731995, -1.0609854180729585, 1.2055079392410735, -1.0595465449261656, 1.2193267364449674, -0.4971537816093221, 1.2570674433270763, -0.23804365678930872, 1.2666079847710938, -0.35230993232006913, 1.2685572320277818, -0.7162195229613343, 1.2822062489399069, -0.5729673802466805, 1.2867476970305176, -1.085458102451899, 1.3173377100446988, -0.24451553056607364, 1.3300674258049223, -0.712164463090006, 1.3440673103856573, -0.858045057193232, 1.3581263470428868, -0.595386951466421, 1.364426387227398, -0.4965687267487062, 1.370577582830267, -1.781166022877449, 1.4048468419071178, -0.6821142806635024, 1.415026419981169, -0.246469321377049, 1.4278075689233756, -0.9399645107317163, 1.466526765217697, -0.6553466370724784, 1.4726874349285675, -0.21989718742837083, 1.480637340444023, -1.1856770194341955, 1.4808470703032148, -0.20202632066699883, 1.4826476016250292, -0.8003201048392959, 1.4826666765525758, -0.4378207086085346, 1.4863173961173646, -0.632495056634289, 1.487747902775823, -0.29591051535705887, 1.4886672547444058, -0.6922810807384852, 1.5090967736697527, -0.6401147178247201, 1.5243365908974646, -0.36793111818127827, 1.5353381333581997, -0.1000763617487289, 1.5376574215595726, -0.6076293827923834, 1.5459773848086527, -0.5447809925478609, 1.5491073583047734, -0.8550004729273551, 1.549406719580107, -0.5303492287480543, 1.5569979028948042, -0.3029366765950565, 1.5705878913433908, -1.1707408299836426, 1.576796219746407, -0.5752812933975688, 1.5770880949349861, -0.6846360539223629, 1.5800673850263687, -0.7571971618176796, 1.5811164354944685, -0.632547090351008, 1.582386721540752, -0.48097505406563595, 1.591017391623645, -2.1592209658853396, 1.5949579967382577, -2.146982387727972, 1.5977465494805938, -0.8970219063648865, 1.6139876985936736, -0.5756853697093267, 1.6174666943933222, -0.6617564447353115, 1.6322371867450027, -1.3354878746553662, 1.638157571172899, -0.5044721449685798, 1.6514479526800827, -0.42749108642745887, 1.6700274463568014, -0.4258399729366533, 1.6742675055412308, -0.8611113135645894, 1.6745173901890575, -0.4297360286232221, 1.6752478983248855, -0.728661148306889, 1.6760279652457082, -0.31650977653041085, 1.6823470393815103, -0.08056595691551174, 1.6968580808314584, -0.5730593240781079, 1.702147178556663, -1.256028966228925, 1.7024676261052494, -0.4846135377389992, 1.7038581663104049, -0.872752666108803, 1.7056566773334285, -0.3965830520782462, 1.715077104766631, -1.2326200387627084, 1.727566488236293, -0.5330859844516418, 1.7332274166571036, -0.5623285040806418, 1.7392566054813918, -1.938247448060963, 1.7692573119135642, -0.6546730536106997, 1.774388068365666, -0.32253173831391646, 1.7888667076513587, -0.2522068150434009, 1.7913367372116278, -0.4087371813562308, 1.8110072311304772, -1.1324515170584746, 1.8270480359035115, -0.7081751493788383, 1.8379370121176237, -2.911462366705391, 1.846237863752092, -0.9772687318690354, 1.848896756402919, -0.7245326615049615, 1.8499876905591097, -0.6746682136477867, 1.8690668947548945, -0.9305701647637656, 1.8779780939757007, -0.24932060008081824, 1.8804175828222056, -0.5535964331650711, 1.8845069383951292, -0.45571581474593403, 1.8960769733743472, -0.7572659948659859, 1.9130274812569383, -0.788307803051981, 1.9181162824171738, -0.874118273135076, 1.9447963131521928, -0.43951988709537815, 1.9463374254169796, -1.0414388244661539, 1.949526485492594, -0.2706865459499057, 1.950407708408754, -0.3318136200172429, 1.955687222120641, -0.45607537202951337, 1.9629371407487075, -1.3556197135289298, 1.9791380803269505, -0.4194965126503937, 1.987856645265883, -1.1262909936750078, 2.0145766268584553, -0.5669737912583277, 2.0203062932988325, -0.5712229242900525, 2.030247455977087, -0.3032508462608678, 2.0336863380926675, -0.5721936627392026, 2.0554473192037874, -0.8161733041939978, 2.055796347253344, -1.0762988344982662, 2.056307468425752, -0.9847269218420853, 2.087826485298147, -1.3256094943928, 2.1014869114418393, -0.5822246671849951, 2.108887339900081, -0.28010183641729103, 2.1101977308490327, -0.8168199748447807, 2.1164862605248453, -0.3509412349910075, 2.1185176197215707, -0.940424164682928, 2.1190477849274765, -0.5467558376359751, 2.1377875383445577, -0.27207996936630136, 2.141667059686809, -0.5535014611828157, 2.151917200385828, -0.20183183035498173, 2.1551367372064316, -0.404818945929413, 2.1557470907259693, -0.651907487758775, 2.1917464007518817, -0.8133123019888542, 2.193726310257348, -0.3921737736855697, 2.200056718754773, -0.4666573356798762, 2.2012068560411966, -0.48786673572764633, 2.2138868951999786, -1.19875326121424, 2.2175566922394907, -0.03921253327962273, 2.2330767189655845, -1.3350243264607693, 2.234417612024656, -0.4773201180399702, 2.2564379776561876, -0.5926206834919274, 2.265896591390566, -0.46020810563698705, 2.2699267242969885, -0.5064932461593712, 2.284147885906738, -0.7870545109272366, 2.29038689447919, -0.6377103691123636, 2.2920271594232, -0.9298952170069366, 2.3004271727702927, -0.5040601905675711, 2.300617816685504, -1.3138014803157845, 2.30552745990791, -1.2679735410575308, 2.3157165037015788, -0.3009256415981394, 2.320446662976822, -2.6563248081187463, 2.3233363976186245, -0.7441583853110872, 2.325056776883163, -0.7771578115421436, 2.325077731836087, -0.421640258760188, 2.349858002802321, -0.30527547513513503, 2.3686377467704007, -0.7017150201218175, 2.3778864671285596, -0.34846605460500985, 2.3869674030074304, -0.40318518200943926, 2.404997553705116, -0.37414227365676245, 2.4118679025238348, -0.44939984183214987, 2.4455878811989056, -2.743333598773373, 2.4537169708858957, -1.3524478773766284, 2.4618079771172283, -0.49531188334065895, 2.4634273230562864, -0.8543430335860395, 2.4660670220421355, -1.1334526921502537, 2.482287152404623, -0.5210450158215268, 2.4879576779377484, -0.375017826361449, 2.494337782738938, -0.8739043650380248, 2.4971167445162017, -0.5736477736550497, 2.4992873374870563, -0.5554629777451493, 2.5302263615308247, -0.2864746696952189, 2.538746560123578, -0.4747008124672706, 2.546627742844501, -0.819477373884559, 2.548657120684623, -0.40255219170614476, 2.5628477901652054, -0.5812263902553939, 2.564987873281165, -1.1262782912771785");
        final StringBuilder bes = new StringBuilder("1.0");
        final StringBuilder bis = new StringBuilder("0.7772374771698007, -0.6770753835775345");

        final MatrixStore<Double> Q = MatrixReader.readMatrix(qs.toString(), 255, 255);

        //MatrixStore<Double> QQ = MatrixReader.readMatrix("U:/ojalgo_test/Q.txt");        
        //for (int row = 0; row < 255; ++row) {
        //    for (int col = 0; col < 255; ++col) {
        //        double qq = QQ.get(row, col);
        //        double q = Q.get(row, col);
        //        double diff = Math.abs(qq - q);
        //        if (diff >= 1.e-5) {
        //            System.out.println("problem at (" + row + ", " + col + "); diff = " + diff);
        //        }
        //     }
        // }

        final MatrixStore<Double> AE = MatrixReader.readMatrix(aes.toString(), 1, 255);
        final MatrixStore<Double> AI = MatrixReader.readMatrix(ais.toString(), 2, 255);
        final MatrixStore<Double> BE = MatrixReader.readMatrix(bes.toString(), 1, 1);
        final MatrixStore<Double> BI = MatrixReader.readMatrix(bis.toString(), 2, 1);
        final MatrixStore<Double> C = PrimitiveDenseStore.FACTORY.makeZero(255, 1);

        if (Q != null) {
            final ExpressionsBasedModel model = QuadraticDemo.createQuadraticModel((int) Q.countColumns(), Q, C, AE, BE, AI, BI);
            for (final Variable v : model.getVariables()) {
                v.lower(BigMath.ZERO).upper(BigMath.ONE.divide(BigMath.HUNDRED));
            }
            System.out.println(model);
            model.setMinimisation();
            final ConvexSolver solver = QuadraticDemo.createConvexSolver(model);
            final double start = System.currentTimeMillis();
            final Optimisation.Result res = solver.solve();
            final double time = (System.currentTimeMillis() - start) / 1000.0D;
            System.out.println(BigMatrix.FACTORY.columns(res));
            System.out.println(res.getState());
            System.out.println("time: " + time);
        }

    }

}