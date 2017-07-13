package com.jameswong.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_profession)
    TextView mTvProfession;
    @BindView(R.id.tv_gender)
    TextView mTvGender;
    @BindView(R.id.tv_education)
    TextView mTvEducation;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_mail)
    TextView mTvMail;
    @BindView(R.id.tv_years_of_work)
    TextView mTvYearsOfWork;
    @BindView(R.id.tv_intention)
    TextView mTvIntention;
    @BindView(R.id.tv_experience)
    TextView mTvExperience;
    @BindView(R.id.tv_github)
    TextView mTvGithub;
    @BindView(R.id.tv_career_planning)
    TextView mTvCareerPlanning;
    @BindView(R.id.tv_self_evaluation)
    TextView mTvSelfEvaluation;
    @BindView(R.id.tv_project_experience)
    TextView mTvProjectExperience;
    @BindView(R.id.tv_skill_container)
    TextView mTvSkillContainer;
    @BindView(R.id.tv_old)
    TextView mTvOld;
    private final String BRITHDAY = "1994-03-07";
    private final String STARTWORK = "2017-03-04";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();

        mTvExperience.append(loadDataWithIO("工作经历.txt"));
        mTvSelfEvaluation.append(loadDataWithIO("自我评价.txt"));
        mTvCareerPlanning.append(loadDataWithIO("职业规划.txt"));
        mTvProjectExperience.append(loadDataWithIO("职业规划.txt"));
        mTvSkillContainer.append(loadDataWithIO("专业技能.txt"));

        mTvOld.append("出生"+loadOld(BRITHDAY));
        mTvYearsOfWork.append("工作"+loadOld(STARTWORK));
    }

    /**
     * 计算出生至今年月日
     *
     * @param brithday
     * @return
     */
    private String loadOld(String brithday) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int birth_year = 0;
        int birth_month = 0;
        int birth_day = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(brithday);
            calendar.setTime(date);
            birth_year = calendar.get(Calendar.YEAR);
            birth_month = calendar.get(Calendar.MONTH) + 1;
            birth_day = calendar.get(Calendar.DAY_OF_MONTH);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        int compared_year = year - birth_year;
        int compared_month = month - birth_month;
        int compared_day = day - birth_day;
        if (compared_month > 0) {
            if (compared_day < 0) {
                compared_month -= 1;
                compared_day = calculateMonth(birth_month, year) - birth_day + day;
            }
        } else if (compared_month < 0) {
            compared_year -= 1;
            compared_month = 12 - birth_month + month;
            if (compared_day < 0) {
                compared_month -= 1;
                compared_day = calculateMonth(birth_month, year) - birth_day + day;
            }

        } else {
            if (compared_day < 0) {
                compared_month = 11;
                compared_day = calculateMonth(birth_month, year) - birth_day + day;
            }
        }
        return compared_year + "年" + compared_month + "月" + compared_day + "天";
    }

    private int calculateMonth(int birth_month, int year) {
        if (birth_month == 2) {
            if (isLeapYear(year)) {
                return 28;
            }
        } else if (birth_month == 1 || birth_month == 3 || birth_month == 5 || birth_month == 7 || birth_month == 8 || birth_month == 10 || birth_month == 12) {
            return 31;
        } else {
            return 30;
        }
        return 0;
    }

    /**
     * io流读取assets中的txt，显示到TextView上
     *
     * @param file 文件名
     * @return
     */
    private String loadDataWithIO(String file) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;
        try {
            inputStream = getResources().getAssets().open(file);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return stringBuilder.toString();
    }

    /**
     * 初始化基本信息
     */
    private void initData() {
        mTvName.append("王施君");
        mTvProfession.append("软件工程");
        mTvGender.append("男");
        mTvEducation.append("本科");
        mTvPhone.append("13913778859");
        mTvMail.append("PassionateWsj@outlook.com");
        mTvIntention.append("Android工程师");
        mTvGithub.append("https://github.com/PassionateWsj");
    }


    /**
     * 判断是否闰年
     *
     * @return
     */
    public static boolean isLeapYear(int year) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }
}
